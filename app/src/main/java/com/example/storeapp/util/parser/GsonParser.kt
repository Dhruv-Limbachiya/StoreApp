package com.example.storeapp.util.parser

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by Dhruv Limbachiya on 06-09-2022.
 */
class GsonParser(
    private val gson: Gson
) : JsonParser {

    /**
     * This method deserializes the specified Json into an object of the specified type
     */
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    /**
     * This method serializes the specified object into its equivalent Json representation
     */
    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}