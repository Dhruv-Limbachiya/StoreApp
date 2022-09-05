package com.example.storeapp.data.cache.entity

data class Cart(
    val id: Int,
    val userId: Int,
    val products: List<CartItem>
)

data class CartItem(
    val quantity: Int,
    val productId: Int
) {
    companion object {
        val INVALID_CART_ITEM = CartItem(0,0)
    }
}