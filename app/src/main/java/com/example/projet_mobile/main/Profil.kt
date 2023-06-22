package com.example.projet_mobile.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentLoginBinding
import com.example.projet_mobile.databinding.FragmentProfilBinding
import com.example.projet_mobile.login.LoginFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profil : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set click listener for the logout button
        binding.logout.setOnClickListener {
            findNavController().navigate(R.id.action_profil_to_home)
         //   logout()
        }

        return view
    }

    private fun logout() {
        // Perform logout logic here
        // For example, clear user session, navigate to login screen, etc.

        // Start LoginFragment or your desired login screen
        val loginFragment = LoginFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.action_profil_to_home, loginFragment)
            .commit()

        // Finish the current activity or fragment (if it's inside a container)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profil().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
