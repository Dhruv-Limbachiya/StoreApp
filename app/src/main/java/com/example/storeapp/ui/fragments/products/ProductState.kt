package com.example.storeapp.ui.fragments.products

import com.example.storeapp.data.cache.entity.ProductEntity

data class ProductState(
    var products: List<ProductEntity>? = emptyList(),
    var errorMessage: String?,
    var isLoading: Boolean = false
) {
    companion object {
        val INVALID_PRODUCT_STATE = ProductState(emptyList(),"",false)
    }
}
