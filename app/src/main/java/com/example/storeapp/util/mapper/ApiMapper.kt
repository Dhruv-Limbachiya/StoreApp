package com.example.storeapp.util.mapper

/**
 * An interface responsible for mapping entity object to domain object.
 */
interface ApiMapper<E, D> {
    fun mapToDomain(apiEntity: E): D
}