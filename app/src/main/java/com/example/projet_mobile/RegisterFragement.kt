package com.example.projet_mobile


import android.os.Bundle
import android.view.View
import com.example.projet_mobile.databinding.FragmentRegisterBinding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.example.mynavigation.retrofit.Endpoint
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.RestaurentAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    lateinit var myModel: MyModel

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
            myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val address = binding.etAddress.text.toString()
            val password = binding.etPassword.text.toString()


            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Une erreur s'est  ", Toast.LENGTH_SHORT).show()
                }
            }


            CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
                try {
                    val response = Endpoint.createEndpoint().registerUser(

                        Endpoint.RegisterRequest(
                            name,
                            email,
                            password,
                            address,
                            phoneNumber,

                        )
                    )


                    if (response.isSuccessful && response.body() != null) {
                        val userResponse = response.body()!!
                        myModel.user = userResponse.user
                        Toast.makeText(requireActivity(), "Account created successfully, please login!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_register_to_login)



//                            findNavController().navigate(R.id.action_homeFragment_to_restaurantFragment)

                    } else {
                        Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    val errorMessage = "Une erreur s'est produite: ${e.message}"
                    Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}