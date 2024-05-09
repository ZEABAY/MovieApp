package com.zaaydar.movieapp.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

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
        val email = binding.etUserMail.text.toString()
        val password = binding.etUserPassword.text.toString()
        binding.etHeader.text = getString(R.string.signing_in)

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
                    authResult.user?.let { user ->
                        userUUID = user.uid
                    }

                FirebaseFirestore.getInstance().collection("favoriteMovies").document(userUUID)
                        .get().addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                favorites =
                                    (documentSnapshot["favs"] as? ArrayList<Long> ?: arrayListOf())
                                println(favorites)
                            }
                            intentFromMainToHome()
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                context, e.localizedMessage, Toast.LENGTH_SHORT
                            ).show()
                            binding.etHeader.text = getString(R.string.sign_in)
                            binding.btnSignIn.isEnabled = true
                        }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        context, e.localizedMessage, Toast.LENGTH_SHORT
                    ).show()
                    binding.etHeader.text = getString(R.string.sign_in)
                    binding.btnSignIn.isEnabled = true
                }
        } else {
            Toast.makeText(
                context,
                "Enter Email And Password",
                Toast.LENGTH_SHORT
            ).show()
            binding.btnSignIn.isEnabled = true
        }
    }

    private fun intentFromMainToHome() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
