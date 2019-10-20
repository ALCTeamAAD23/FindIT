package com.example.findit.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

/**
 * Borrowed from https://developer.android.com/guide/navigation/navigation-conditional
 */
class LoginViewModel : ViewModel() {

    /**
     * Authentication states
     */
    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED  ,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    /**
     * Firebase Authentication Instance
     */
    private val auth = FirebaseAuth.getInstance()

    val authenticationState = MutableLiveData<AuthenticationState>()


    init {
        // If there is the instance of the current user set the
        // AuthenticationState as Authenticated otherwise unauthenticated
        if (auth.currentUser == null) {
            refuseAuthentication()
        } else {
            acceptAuthentication()
        }

        try {
            auth.currentUser?.reload()
        } catch (e: FirebaseAuthInvalidUserException) {
            refuseAuthentication()
        }
    }

    fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        acceptAuthentication()
                    } else {
                        invalidateAuthentication()
                    }
                }
    }

    /**
    * Signs out the user abd sets the authentication an unauthenticated
    */
    fun signOut() {
        auth.signOut()
        refuseAuthentication()
    }

    /**
     * Accept authentication
     */
    private fun acceptAuthentication() {
        authenticationState.value = AuthenticationState.AUTHENTICATED
    }

    /**
     * Invalidate authentication
     */
    private fun invalidateAuthentication() {
        authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
    }

    /**
     * Unauthenticated state
     */
    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}