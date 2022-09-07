package com.example.storeapp.data.cache.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Helper class that helps to retrieve product info based on relation between listed product and cart item.
 * Uses id to map relation between two entities.
 */
data class GeneralProductAndCartProduct(
    @Embedded
    val productEntity: ProductEntity?,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "cart_product_id"
    )
    val cart: CartItemEntity?
)