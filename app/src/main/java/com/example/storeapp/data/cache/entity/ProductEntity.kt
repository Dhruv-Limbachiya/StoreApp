package com.example.storeapp.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val category: String
) {
    companion object {
        val INVALID_PRODUCT = ProductEntity(0,"",0.0,"","")
    }
}