package com.example.findit


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentLoginBinding
import com.example.findit.viewmodels.LoginViewModel

/**
 * Login Fragment
 */
class LoginFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        val navController = findNavController()

        // Listen for the click to the Login button
        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.group.visibility = View.GONE

            if (binding.emailPrompt.text.isNullOrBlank() || binding.passwordPrompt.text.isNullOrBlank()) {
                viewModel.refuseAuthentication()
            } else {
                viewModel.authenticate(binding.emailPrompt.text.toString(), binding.passwordPrompt.text.toString())
            }
        }

        // Don't have an account go to register page
        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Observe the authentication state
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(R.id.action_loginFragment_to_homeFragment)
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> {
                    Toast.makeText(requireContext(), "Invalid Email or password", Toast.LENGTH_LONG).show()
                }
                else -> Log.d("LoginFragment", "Stateless")
            }

            binding.progressBar.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
        })

        return binding.root
    }


}
