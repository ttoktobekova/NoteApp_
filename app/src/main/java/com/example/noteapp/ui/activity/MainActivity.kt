package com.example.noteapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.ui.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var pref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = PreferenceHelper()
        pref.unit(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        isView()
        binding.bottomNav.setupWithNavController(navController)
        viewBottomNavView()
    }

    private fun isView() {
        if (!pref.isPlay) {
            navController.navigate(R.id.onBoardFragment)
        } else {
            navController.navigate(R.id.noteFragment)
        }
    }

    private fun viewBottomNavView() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardFragment, R.id.onBoardPagingFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }

                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}