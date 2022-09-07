package com.example.storeapp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityMainBinding
import com.example.storeapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initComponents()
    }

    private fun initComponents() {
        setNavToolBar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setNavToolBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController // find NavController using NavHostFragment

        // Configure App bar by specifying all top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.productsFragment))

        // sets custom background for built in action bar(toolbar)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.drawable_gradient_splash_background))

        // set nav controller and app bar configuration instance to the default action bar(toolbar).
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        // Triggers double back press logic when back stack is empty. In Layman's term,triggers when no activities/fragment to pop.
        if (!navController.popBackStack()) {
            exitOnDoubleBackPress()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}