package com.example.storeapp.repository

import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.model.ApiCart
import com.example.storeapp.data.model.ApiProduct
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class StoreAppRepositoryImpl @Inject constructor(
    private val storeAPI: StoreAPI,
    private val storeAppDatabase: StoreAppDatabase
) : StoreAppRepository {

    override fun getAllProducts(): Flow<Resource<List<ApiProduct>>> {
        TODO("Not yet implemented")
    }

    override fun getProductsByCategory(category: String): Flow<Resource<List<ApiProduct>>> {
        TODO("Not yet implemented")
    }

    override fun getProductsById(productId: Int): Flow<Resource<List<ApiProduct>>> {
        TODO("Not yet implemented")
    }

    override fun getCartItems(): Flow<Resource<List<ApiCart>>> {
        TODO("Not yet implemented")
    }
}