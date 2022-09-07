package com.example.storeapp.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storeapp.data.cache.dao.CartsDao
import com.example.storeapp.data.cache.dao.ProductsDao
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, CartItemEntity::class],
    exportSchema = false,
    version = 1
)
abstract class StoreAppDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao
    abstract val cartsDao: CartsDao
}