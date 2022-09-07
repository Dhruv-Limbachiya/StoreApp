package com.example.storeapp.repository.implementation

import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.repository.interfaces.ProductsRepository
import com.example.storeapp.util.NetworkHelper
import com.example.storeapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ProductsRepositoryImpl @Inject constructor(
    private val storeAPI: StoreAPI,
    private val storeAppDatabase: StoreAppDatabase,
    private val networkHelper: NetworkHelper
) : ProductsRepository {

    override fun getAllProducts() = flow {
        emit(Resource.Loading()) // emit the loading state
        delay(1000L) // simulate processing requests to make progressbar visible for certain amount of time

        val cacheProducts = storeAppDatabase.productsDao.getAllProducts()

        if (cacheProducts?.isNotEmpty() == true) {
            emit(Resource.Success(cacheProducts))
            return@flow
        }

        try {
            // Fetch products from the api if and only if there are no product in "products" table
            if (networkHelper.isInternetConnected()) {
                val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                storeAppDatabase.productsDao.insertAllProducts(remoteProducts)
                emit(Resource.Success(remoteProducts))
            } else {
                emit(Resource.Error("No internet connection", emptyList<ProductEntity>()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Oops,something went wrong!", emptyList<ProductEntity>()))
        } catch (e: IOException) {
            Resource.Error(
                "Couldn't reach the server.Check your internet connection",
                emptyList<ProductEntity>()
            )
        }
    }

    override fun getProductsByCategory(category: String) = flow {
        emit(Resource.Loading()) // emit the loading state

        val cacheProducts = storeAppDatabase.productsDao.getAllProducts()

        if (cacheProducts?.isEmpty() == true) {
            try {
                // Fetch products from the api if and only if there are no product in "products" table
                if (networkHelper.isInternetConnected()) {
                    val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                    storeAppDatabase.productsDao.insertAllProducts(remoteProducts)
                } else {
                    emit(Resource.Error("No internet connection", emptyList<ProductEntity>()))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Oops,something went wrong!", emptyList<ProductEntity>()))
            } catch (e: IOException) {
                Resource.Error(
                    "Couldn't reach the server.Check your internet connection",
                    emptyList<ProductEntity>()
                )
            }
        }

        // Query database to fetch products by category.
        val cacheProductsByCategory = storeAppDatabase.productsDao.getProductsByCategory(category)

        if (cacheProductsByCategory?.isNotEmpty() == true) {
            emit(Resource.Success(cacheProductsByCategory))
        } else {
            emit(
                Resource.Error(
                    "Record not found!",
                    emptyList()
                )
            )
        }
    }

    override fun getProductsById(productId: Int) = flow {
        emit(Resource.Loading()) // emit the loading state

        val cacheProducts = storeAppDatabase.productsDao.getAllProducts()

        if (cacheProducts?.isEmpty() == true) {
            try {
                // Fetch products from the api if and only if there are no product in "products" table
                if (networkHelper.isInternetConnected()) {
                    val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                    storeAppDatabase.productsDao.insertAllProducts(remoteProducts)
                } else {
                    emit(Resource.Error("No internet connection", emptyList<ProductEntity>()))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Oops,something went wrong!", emptyList<ProductEntity>()))
            } catch (e: IOException) {
                Resource.Error(
                    "Couldn't reach the server.Check your internet connection",
                    emptyList<ProductEntity>()
                )
            }
        }

        // Query database to fetch products by id.
        val cacheProductsById = storeAppDatabase.productsDao.getProductsById(productId)

        if (cacheProductsById?.isNotEmpty() == true) {
            emit(Resource.Success(cacheProductsById))
        } else {
            emit(
                Resource.Error(
                    "Record not found!",
                    emptyList()
                )
            )
        }
    }
}