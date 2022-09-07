package com.example.storeapp.repository.interfaces

import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllProducts(): Flow<Resource<List<ProductEntity>>>
    fun getProductsByCategory(category: String): Flow<Resource<List<ProductEntity>>>
    fun getProductsById(productId: Int): Flow<Resource<List<ProductEntity>>>
}