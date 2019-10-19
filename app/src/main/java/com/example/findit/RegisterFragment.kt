package com.example.findit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentRegisterBinding
import com.example.findit.viewmodels.RegisterViewModel

/**
 * Registration fragment
 */
class RegisterFragment : Fragment() {

    private val viewModel by activityViewModels<RegisterViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        navController = findNavController()

        // Create account
        binding.registerButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.group.visibility = View.GONE

            if (!binding.emailPrompt.text.isNullOrBlank() &&
                    !binding.passwordPrompt.text.isNullOrBlank() &&
                    binding.passwordPrompt.text.toString() == binding.confirmPasswordPrompt.text.toString()) {

                val email = binding.emailPrompt.text.toString()
                val password = binding.passwordPrompt.text.toString()
                viewModel.createAccount(email, password)
            } else {
                viewModel.registrationFailed()
            }
        }

        // Already have an account navigate to login
        binding.loginButton.setOnClickListener {
            cancelRegistration()
        }

        // If the user presses back, cancel the user registration and pop back
        // to the login fragment. Since this ViewModel is shared at the activity
        // scope, its state must be reset so that it will be in the initial
        // state if the user comes back to register later.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelRegistration()
        }

        viewModel.registrationState.observe(viewLifecycleOwner, Observer {
            if (it == RegisterViewModel.RegistrationState.REGISTRATION_COMPLETED) {
                Toast.makeText(requireContext(), "Registration successful: You can now login", Toast.LENGTH_LONG).show()
                navController.popBackStack(R.id.loginFragment, false)
            } else if (it == RegisterViewModel.RegistrationState.REGISTRATION_FAILED) {
                Toast.makeText(requireContext(), "Registration failed: Information invalid", Toast.LENGTH_LONG).show()
            }

            binding.progressBar.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
        })

        return binding.root
    }

    private fun cancelRegistration() {
        viewModel.userCancelledRegistration()
        navController.popBackStack(R.id.loginFragment, false)
    }


}
