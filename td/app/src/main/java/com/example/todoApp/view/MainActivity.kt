package com.example.todoApp.view

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todoApp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar2 = this.supportActionBar!!

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
      //  val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        //bottomNavigationView.isClickable = false


    }

    companion object{
            lateinit var actionBar2 : androidx.appcompat.app.ActionBar
            lateinit var bottomNavigationView: BottomNavigationView
            //var bar : Boolean = false
            fun bar2(barTitle2: String){
                if (actionBar2 != null)
                    actionBar2.title = barTitle2
            }

        fun enableBottomNav(bar : Boolean){
            bottomNavigationView.menu.getItem(0).isEnabled= bar
            bottomNavigationView.menu.getItem(1).isEnabled= bar
        }

    }
}