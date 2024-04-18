package com.zaaydar.movieapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.zaaydar.movieapp.databinding.FragmentProfileBinding
import com.zaaydar.movieapp.ui.login.LoginActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            btnFavs.setOnClickListener { toFavoritesFragment() }
            btnSignOut.setOnClickListener { signOut() }
        }
    }

    private fun toFavoritesFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment()
        findNavController().navigate(action)
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}
