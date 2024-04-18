package com.zaaydar.movieapp.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.FragmentSignInBinding
import com.zaaydar.movieapp.ui.main.MainActivity
import com.zaaydar.movieapp.util.Constants.favorites
import com.zaaydar.movieapp.util.Constants.userUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private var isSignIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnSignIn.setOnClickListener { signIn() }

    }

    private fun signIn() {
        if (isSignIn) return

        val email = binding.etUserMail.text.toString()
        val password = binding.etUserPassword.text.toString()
        binding.etHeader.text = getString(R.string.signing_in)
        isSignIn = true

        if (email.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val authResult = auth.signInWithEmailAndPassword(email, password).await()
                    authResult.user?.let { user ->
                        userUUID = user.uid
                    }

                    val documentSnapshot = FirebaseFirestore.getInstance()
                        .collection("favoriteMovies")
                        .document(userUUID)
                        .get().await()

                    if (documentSnapshot.exists()) {
                        favorites = (documentSnapshot["favs"] as? ArrayList<Long> ?: arrayListOf())
                        println(favorites)
                    }

                    launch(Dispatchers.Main) {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            e.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.etHeader.text = getString(R.string.sign_in)
                    }
                } finally {
                    binding.btnSignIn.isEnabled = true
                    isSignIn = false
                }
            }
        } else {
            Toast.makeText(
                context,
                "Enter Email And Password",
                Toast.LENGTH_SHORT
            ).show()
            binding.btnSignIn.isEnabled = true
            isSignIn = false
        }
    }
}