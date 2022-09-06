package com.example.storeapp.repository

import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.data.model.ApiCart
import com.example.storeapp.data.model.ApiProduct
import com.example.storeapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StoreAppRepository {
    /* abstract methods for screen 1 (products list screen) */
    fun getAllProducts(): Flow<Resource<List<ProductEntity>>>
    fun getProductsByCategory(category: String): Flow<Resource<List<ProductEntity>>>
    fun getProductsById(productId: Int): Flow<Resource<List<ProductEntity>>>

    /* abstract methods for screen 2 (carts list screen) */
    fun getCartItems() : Flow<Resource<List<GeneralProductAndCartProduct>>>
}