package com.example.storeapp.data.cache.dao

import androidx.room.*
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity

/*
 * Carts Dao to interact with carts table in db
 */
@Dao
interface CartsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCarts(cartItemEntity: List<CartItemEntity>)

    @Query("SELECT * FROM carts ")
    fun getAllCarts(): List<CartItemEntity>?

    @Query("DELETE FROM carts")
    suspend fun deleteAllCartItems()

    @Transaction
    @Query("SELECT * FROM products")
    fun getCartItems(): List<GeneralProductAndCartProduct>?
}