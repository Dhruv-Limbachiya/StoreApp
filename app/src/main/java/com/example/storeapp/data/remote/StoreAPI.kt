package com.example.storeapp.data.remote

import com.example.storeapp.data.model.ApiCart
import com.example.storeapp.data.model.ApiProduct
import com.example.storeapp.data.remote.EndPoints.API_CARTS
import com.example.storeapp.data.remote.EndPoints.API_PRODUCTS
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreAPI {
    @GET(API_PRODUCTS)
    suspend fun getAllProduct(): List<ApiProduct>

    @GET("$API_CARTS/{id}")
    suspend fun getCarts(@Path("id") productId: Int): ApiCart
}