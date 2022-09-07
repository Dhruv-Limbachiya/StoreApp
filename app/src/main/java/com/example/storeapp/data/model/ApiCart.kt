package com.example.storeapp.data.model

import com.example.storeapp.data.cache.entity.Cart
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.CartItemEntity.Companion.INVALID_CART_ITEM
import com.example.storeapp.util.mapper.ApiMapper
import com.google.gson.annotations.SerializedName

/**
 * Models cart object received from api response
 */
data class ApiCart(
    val date: String? = null,
    @field:SerializedName("__v")
    val V: Int? = null,
    val id: Int? = null,
    val userId: Int? = null,
    val products: List<ApiCartProduct?>? = null
) : ApiMapper<ApiCart, Cart> {

    /**
     * Maps modeled object into domain(use-case specific) object
     */
    override fun mapToDomain(apiEntity: ApiCart): Cart {
        return Cart(
            id = id ?: 0,
            userId = userId ?: 0,
            products = products.orEmpty().map {
                it?.mapToDomain(it) ?: INVALID_CART_ITEM
            }
        )
    }
}

/**
 * Models cart object received from api response
 */
data class ApiCartProduct(
    val quantity: Int? = null,
    val productId: Int? = null
) : ApiMapper<ApiCartProduct, CartItemEntity> {

    /**
     * Maps modeled object into domain(use-case specific) object
     */
    override fun mapToDomain(apiEntity: ApiCartProduct): CartItemEntity {
        return CartItemEntity(
            quantity ?: 0,
            productId ?: 0
        )
    }
}


