package com.example.storeapp.repository

import com.example.storeapp.data.model.ApiCart
import com.example.storeapp.data.model.ApiProduct
import com.example.storeapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StoreAppRepository {
    /* abstract methods for screen 1 (products list screen) */
    fun getAllProducts(): Flow<Resource<List<ApiProduct>>>
    fun getProductsByCategory(category: String): Flow<Resource<List<ApiProduct>>>
    fun getProductsById(productId: Int): Flow<Resource<List<ApiProduct>>>

    /* abstract methods for screen 2 (carts list screen) */
    fun getCartItems() : Flow<Resource<List<ApiCart>>>
}