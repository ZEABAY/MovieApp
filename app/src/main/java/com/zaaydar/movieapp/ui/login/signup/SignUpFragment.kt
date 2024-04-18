package com.zaaydar.movieapp.ui.login.signup

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
import com.zaaydar.movieapp.databinding.FragmentSignUpBinding
import com.zaaydar.movieapp.ui.main.MainActivity
import com.zaaydar.movieapp.util.Constants


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private var isSignup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnHaveAcc.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnRegister.setOnClickListener { signUp() }
    }

    private fun signUp() {
        if (isSignup) return

        val email = binding.etUserMail.text.toString()
        val password = binding.etUserPassword.text.toString()
        binding.etHeader.text = getString(R.string.signing_in)
        isSignup = true

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { user ->
                        Constants.userUUID = user.uid
                    }

                    FirebaseFirestore.getInstance()
                        .collection("favoriteMovies")
                        .document(Constants.userUUID)
                        .get().addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                Constants.favorites =
                                    (documentSnapshot["favs"] as? ArrayList<Long> ?: arrayListOf())

                                println(Constants.favorites)
                            }

                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        it.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnCompleteListener {
                    isSignup = false
                    binding.btnRegister.isEnabled = true
                }
        } else {
            Toast.makeText(
                context,
                "Enter Email And Password",
                Toast.LENGTH_SHORT
            ).show()
            isSignup = false
        }
        binding.etHeader.text = getString(R.string.sign_up)
    }

}