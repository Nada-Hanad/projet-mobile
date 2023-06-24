package com.example.projet_mobile.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.mynavigation.retrofit.Endpoint
import com.example.projet_mobile.MainActivity
import com.example.projet_mobile.R
import com.example.projet_mobile.RegisterFragment
import com.example.projet_mobile.databinding.FragmentLoginBinding
import com.example.projet_mobile.main.Profil
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileDescriptor.out
import java.lang.System.out


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    lateinit var myModel: MyModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = binding!!

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext() , gso)
        binding.Logingoogle.setOnClickListener {
            signInGoogle()
        }
        // Inflate the layout for this fragment
        return view
    }
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText((requireContext()), task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(requireContext() , MainActivity::class.java)
                intent.putExtra("email" , account.email)
                intent.putExtra("name" , account.displayName)
             //   startActivity(intent)
                val email = account.email
                val name = account.displayName
                Toast.makeText(requireContext(), email , Toast.LENGTH_SHORT).show()

                val bundle = Bundle()
                bundle.putString("email", email)
                bundle.putString("name", name)
                val profilFragment = Profil()
                profilFragment.arguments = bundle
                findNavController().navigate(R.id.action_loginFragment_to_profil, bundle)
            }else{
                Toast.makeText(requireContext(), it.exception.toString() , Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading
        val signupTextView = binding.signup
        signupTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_register)
        }
        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity(), "Une erreur s'est  ", Toast.LENGTH_SHORT).show()
                }
            }

            myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)

            CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
                try {
                    val response = Endpoint.createEndpoint().loginUser(
                        Endpoint.LoginRequest(
                            usernameEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    )


                    if (response.isSuccessful && response.body() != null) {
                        val userResponse = response.body()!!
                        myModel.user = userResponse.user
                        Toast.makeText(requireActivity(), "Logged In successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                        loadingProgressBar.visibility = View.INVISIBLE

                    } else {
                        loadingProgressBar.visibility = View.INVISIBLE

                        Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    loadingProgressBar.visibility = View.INVISIBLE

                    val errorMessage = "Une erreur s'est produite: ${e.message}"
                    Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_loginFragment_to_profil)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

}