package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_recipes, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return when (p0.itemId) {
            R.id.navigation_recipes -> {
                navController.navigate(R.id.navigation_recipes)
                search_view.isVisible = true
                Log.d("NAVIGATION", "HOME")
                true
            }
            R.id.navigation_dashboard -> {
                navController.navigate(R.id.navigation_dashboard)
                search_view.isVisible = false
                Log.d("NAVIGATION", "DASHBOARD")
                true
            }
            R.id.navigation_notifications -> {
                navController.navigate(R.id.navigation_notifications)
                search_view.isVisible = false
                Log.d("NAVIGATION", "NOTIFICATION")
                true
            }
            else -> false
        }
    }

}
