package com.example.storeapp.data.model

import com.example.storeapp.data.cache.entity.Cart
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.CartItemEntity.Companion.INVALID_CART_ITEM
import com.example.storeapp.util.mapper.ApiMapper
import com.google.gson.annotations.SerializedName

data class ApiCart(
    @field:SerializedName("date")
    val date: String? = null,
    @field:SerializedName("__v")
    val V: Int? = null,
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("userId")
    val userId: Int? = null,
    @field:SerializedName("products")
    val products: List<ApiCartProduct?>? = null
) : ApiMapper<ApiCart, Cart> {

    override fun mapToDomain(apiEntity: ApiCart): Cart {
        return Cart(
            id ?: 0,
            userId ?: 0,
            products.orEmpty().map {
                it?.mapToDomain(it) ?: INVALID_CART_ITEM
            }
        )
    }
}

data class ApiCartProduct(
    @field:SerializedName("quantity")
    val quantity: Int? = null,
    @field:SerializedName("productId")
    val productId: Int? = null
) : ApiMapper<ApiCartProduct, CartItemEntity> {

    override fun mapToDomain(apiEntity: ApiCartProduct): CartItemEntity {
        return CartItemEntity(
            quantity ?: 0,
            productId ?: 0
        )
    }
}


