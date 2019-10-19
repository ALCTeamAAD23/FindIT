package com.example.findit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

/**
 * The main activity that hosts all other fragments in this project
 */
class MainActivity : AppCompatActivity() {

    // Navigation controller for handling the back navigation
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize navigation controller
        navController = findNavController(R.id.nav_container)
    }

    // Back navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}