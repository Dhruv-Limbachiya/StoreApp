package com.example.storeapp.ui.fragments.cart

import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct

/**
 * Represent current state of cart
 */
data class CartState(
    var carts: List<GeneralProductAndCartProduct>? = emptyList(),
    var errorMessage: String?,
    var isLoading: Boolean = false
) {
    companion object {
        val INVALID_CART_STATE = CartState(emptyList(), "", false)
    }
}
