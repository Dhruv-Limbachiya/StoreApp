package com.example.storeapp.data.cache.dao

import androidx.room.*
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity

/*
 * Products Dao to interact with products table in db
 */
@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products ")
    fun getAllProducts(): List<ProductEntity>?

    @Query("SELECT * FROM products WHERE category LIKE '%' || :category || '%' ")
    fun getProductsByCategory(category: String): List<ProductEntity>?

    @Query("SELECT * FROM products WHERE product_id = :productId")
    fun getProductsById(productId: Int): List<ProductEntity>?
}