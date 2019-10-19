package com.example.findit.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Borrowed from https://developer.android.com/guide/navigation/navigation-conditional
 */
class RegisterViewModel : ViewModel() {

    enum class RegistrationState {
        COLLECT_PROFILE_DATA,
        REGISTRATION_COMPLETED,
        REGISTRATION_FAILED
    }

    /**
     * Firebase Authentication Instance
     */
    private val auth = FirebaseAuth.getInstance()

    val registrationState = MutableLiveData<RegistrationState>(RegistrationState.COLLECT_PROFILE_DATA)

    /**
     * Registers a user
     */
    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // If successful registration update the profiles database and
                        // send verification email and logout
                        auth.currentUser!!.sendEmailVerification()
                        // TODO: Add the user information to the database
                        auth.signOut()

                        registrationState.value = RegistrationState.REGISTRATION_COMPLETED
                    } else {
                        registrationFailed()
                    }
                }
    }

    fun registrationFailed() {
        registrationState.value = RegistrationState.REGISTRATION_FAILED
    }

    fun userCancelledRegistration() {
        // Clear existing registration data
        registrationState.value = RegistrationState.COLLECT_PROFILE_DATA
    }
}