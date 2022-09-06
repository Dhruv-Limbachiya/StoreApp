package com.example.storeapp.data.cache.entity

import androidx.room.Embedded
import androidx.room.Relation


data class GeneralProductAndCartProduct(
    @Embedded
    val productEntity: ProductEntity?,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "cart_product_id"
    )
    val cart: CartItemEntity?
)