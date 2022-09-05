package com.example.storeapp.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storeapp.data.cache.dao.StoreDao
import com.example.storeapp.data.cache.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    exportSchema = false,
    version = 1
)
abstract class StoreAppDatabase : RoomDatabase() {
    abstract val storeDao: StoreDao
}