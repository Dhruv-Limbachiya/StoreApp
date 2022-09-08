package com.example.storeapp.ui.fragments.cart

/**
 * Represent current state of cart
 */
data class UIState(
    var message: String?,
    var isSuccess: Boolean = false
) {
    companion object {
        val INVALID_UI_STATE = UIState( null, false)
    }
}
