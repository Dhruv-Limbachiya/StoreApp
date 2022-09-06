package com.example.storeapp.data.cache.dao

import androidx.room.*
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products ")
    fun getAllProducts(): List<ProductEntity>?

    @Query("SELECT * FROM products WHERE category LIKE '%' || :category || '%' ")
    fun getProductsByCategory(category: String): List<ProductEntity>?

    @Query("SELECT * FROM products WHERE product_id = :productId")
    fun getProductsById(productId: Int): List<ProductEntity>?

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCarts(cartItemEntity: List<CartItemEntity>)

    @Query("SELECT * FROM carts ")
    fun getAllCarts(): List<CartItemEntity>?

    @Transaction
    @Query("SELECT * FROM products")
    fun getCartItems(): List<GeneralProductAndCartProduct>?
}