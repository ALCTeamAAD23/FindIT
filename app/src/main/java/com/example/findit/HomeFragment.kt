package com.example.findit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentHomeBinding
import com.example.findit.viewmodels.LoginViewModel

/**
 * Home Fragment container
 */
class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.loginFragment)
                else -> Log.i("HomeFragment", "Logged In")
            }
        })

        return binding.root
    }
}
