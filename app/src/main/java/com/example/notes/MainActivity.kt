package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_recipes, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return goToMenuItem(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return goToMenuItem(item)
    }

    private fun goToMenuItem(item: MenuItem): Boolean {
        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
        return when (item.itemId) {
            R.id.navigation_recipes -> {
                navController.navigate(R.id.navigation_recipes, null, navOptions)
                search_view.isVisible = true
                Log.d("NAVIGATION", "HOME")
                true
            }
            R.id.navigation_dashboard -> {
                navController.navigate(R.id.navigation_dashboard, null, navOptions)
                search_view.isVisible = false
                Log.d("NAVIGATION", "DASHBOARD")
                true
            }
            R.id.navigation_notifications -> {
                navController.navigate(R.id.navigation_notifications, null, navOptions)
                search_view.isVisible = false
                Log.d("NAVIGATION", "NOTIFICATION")
                true
            }
            else -> false
        }
    }
}
