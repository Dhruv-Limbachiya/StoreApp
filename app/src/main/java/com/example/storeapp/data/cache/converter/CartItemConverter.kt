package com.example.storeapp.data.cache.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.util.parser.JsonParser
import com.google.gson.reflect.TypeToken

/**
 * Created by Dhruv Limbachiya on 06-09-2022.
 */
@ProvidedTypeConverter
class CartItemConverter(
    private val jsonParser: JsonParser
) {
    /**
     * Deserializes the specified json into list of Meaning objects
     */
    @TypeConverter
    fun fromCartItemJsonStringToProductList(json: String): List<CartItemEntity> {
        return jsonParser.fromJson<ArrayList<CartItemEntity>>(
            json,
            object : TypeToken<ArrayList<CartItemEntity>>() {}.type // Get the type of an object.
        ) ?: emptyList()
    }

    /**
     * Serializes the list of meaning objects into json string.
     */
    @TypeConverter
    fun fromProductListToCartItemJsonString(meanings: List<CartItemEntity>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<CartItemEntity>>() {}.type // Get the type of an object.
        ) ?: "[]" // empty array.
    }
}