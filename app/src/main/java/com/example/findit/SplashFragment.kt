package com.example.findit

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentSplashBinding
import com.example.findit.viewmodels.LoginViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen
 */
class SplashFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            // Delay the display of login or home screen by 6 seconds using coroutines
            GlobalScope.launch {
                delay(6000L)

                when (it) {
                    LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                        navController.navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                    else -> {
                        navController.navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        })

        viewPropertyAnimator()

        return binding.root
    }

    /**
     * Animate the FindIT logo to rotate and scale in 3 seconds
     */
    private fun viewPropertyAnimator() {
        val vpa = binding.logo.animate()
        vpa.apply {
            duration = 3000
            rotationX(360.0f)
            scaleX(1.5f)
            scaleY(1.5f)
            interpolator = OvershootInterpolator()
            withEndAction {
                fadeAnimation()
            }
            start()
        }
    }

    /**
     * Animate the welcome text to appear in 1 second
     */
    private fun fadeAnimation() {
        val fadeAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha)
        fadeAnimator.apply {
            setTarget(binding.welcomeText)
            start()
        }
    }
}
