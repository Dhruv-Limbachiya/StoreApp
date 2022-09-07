package com.example.storeapp.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Cart(
    val id: Int,
    val userId: Int,
    val products: List<CartItemEntity>
)

/**
 * Represents a cart item in "carts" table
 */
@Entity(tableName = "carts")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cart_product_id")
    val productId: Int,
    val quantity: Int
) {
    companion object {
        val INVALID_CART_ITEM = CartItemEntity(0,0)
    }
}