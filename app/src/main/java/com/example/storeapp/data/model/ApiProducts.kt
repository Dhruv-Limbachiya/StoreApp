package com.example.storeapp.data.model

import android.media.Rating
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.util.mapper.ApiMapper

data class ApiProduct(
    val image: String? = null,
    val price: Double? = null,
    val rating: Rating? = null,
    val description: String? = null,
    val id: Int? = null,
    val title: String? = null,
    val category: String? = null
) : ApiMapper<ApiProduct, ProductEntity> {

    /**
     * Maps api modeled object to app specific domain object.
     */
    override fun mapToDomain(apiEntity: ApiProduct): ProductEntity {
        return ProductEntity(
            image = image.orEmpty(),
            price = price ?: 0.0,
            id = id ?: 0,
            title = title.orEmpty(),
            category = category.orEmpty()
        )
    }
}

data class Rating(
    val rate: Double? = null,
    val count: Int? = null
)
