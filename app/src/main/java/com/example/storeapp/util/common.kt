package com.example.storeapp.util

import android.view.View
import com.example.storeapp.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, isError: Boolean = false) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    val context = this.context

    if (isError) {
        snackBarView.setBackgroundColor(context.resources.getColor(R.color.color_error))
    } else {
        snackBarView.setBackgroundColor(context.resources.getColor(R.color.color_green))
    }

    snackBar.show()
}

fun String.isNumber() = toIntOrNull() != null
