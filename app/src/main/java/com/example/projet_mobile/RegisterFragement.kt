package com.example.projet_mobile


import android.os.Bundle
import android.view.View
import com.example.projet_mobile.databinding.FragmentRegisterBinding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Signin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
        binding.btnRegister.setOnClickListener {
            // Get the username and password from the EditTexts

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val address = binding.etAddress.text.toString()
            val password = binding.etPassword.text.toString()
            //val profilePicture = binding.etProfilePicture.text.toString().trim()
            print(name)
            // TODO: Add database logic to register the user

            // Navigate back to the LoginFragment
            findNavController().navigate(R.id.action_register_to_login)

        }
    }

}