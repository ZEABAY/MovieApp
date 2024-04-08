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
import com.zaaydar.movieapp.databinding.FragmentSignInBinding
import com.zaaydar.movieapp.ui.main.MainActivity

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

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()


        binding.btnSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnSignIn.setOnClickListener { signIn() }

    }

    private fun signIn() {
        val email = binding.etUserMail.text.toString()
        val password = binding.etUserPassword.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        it.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(
                context,
                "Enter Email And Password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}