package com.example.musicbrainz.presentation.screens.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.musicbrainz.R
import com.example.musicbrainz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupDestinationListener(navController)
    }

    private fun setupDestinationListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.home_dest) {

            } else {

            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }
}