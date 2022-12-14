package com.example.storeapp.repository.interfaces

import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Dhruv Limbachiya on 07-09-2022.
 */
interface CartRepository {
    fun getCartItems(): Flow<Resource<List<GeneralProductAndCartProduct>>>
    fun deleteAllCartItems(): Flow<Resource<List<GeneralProductAndCartProduct>>>
}