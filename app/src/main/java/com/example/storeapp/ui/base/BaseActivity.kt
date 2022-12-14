package com.example.storeapp.ui.base

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storeapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private var mDialog: Dialog? = null
    private var mDoubleBackPress = false

    /**
     * Inform user to press back button twice to exit from the app.
     */
    fun exitOnDoubleBackPress() {
        // If user already press back button once then allow user to exit from the app.
        if (mDoubleBackPress) {
            return super.onBackPressed()
        }

        mDoubleBackPress = true

        Toast.makeText(
            this@BaseActivity,
            "Please click BACK again to exit",
            Toast.LENGTH_SHORT
        ).show()

        lifecycleScope.launch {
            /**
             * Wait 2 sec for the user input.
             * If user press back within 2 sec then it will allow to exit otherwise it will reset the
             * backPress boolean and prevent to exit.
             */
            delay(2000)
            mDoubleBackPress = false
        }
    }
}