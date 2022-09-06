package com.example.storeapp.util.parser

import java.lang.reflect.Type

/**
 * Created by Dhruv Limbachiya on 06-09-2022.
 */
interface JsonParser {
    fun <T> fromJson(json: String,type: Type) : T?
    fun <T> toJson(obj : T, type: Type) : String?
}