package com.example.storeapp.repository

import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.util.NetworkHelper
import com.example.storeapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class StoreAppRepositoryImpl @Inject constructor(
    private val storeAPI: StoreAPI,
    private val storeAppDatabase: StoreAppDatabase,
    private val networkHelper: NetworkHelper
) : StoreAppRepository {

    override fun getAllProducts() = flow {
        emit(Resource.Loading())

        delay(1000L)

        val cacheProducts = storeAppDatabase.storeDao.getAllProducts()

        if (cacheProducts?.isNotEmpty() == true) {
            emit(Resource.Success(cacheProducts))
            return@flow
        }

        try {
            if(networkHelper.isInternetConnected()) {
                val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                storeAppDatabase.storeDao.insertAllProducts(remoteProducts)
                emit(Resource.Success(remoteProducts))
            } else {
                emit(Resource.Error("No internet connection",emptyList<ProductEntity>()))
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
        emit(Resource.Loading())

        val cacheProducts = storeAppDatabase.storeDao.getAllProducts()

        if (cacheProducts?.isEmpty() == true) {
            try {
                if(networkHelper.isInternetConnected()) {
                    val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                    storeAppDatabase.storeDao.insertAllProducts(remoteProducts)
                } else {
                    emit(Resource.Error("No internet connection",emptyList<ProductEntity>()))
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
        val cacheProductsByCategory = storeAppDatabase.storeDao.getProductsByCategory(category)

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
        emit(Resource.Loading())

        val cacheProducts = storeAppDatabase.storeDao.getAllProducts()

        if (cacheProducts?.isEmpty() == true) {
            try {
                if(networkHelper.isInternetConnected()) {
                    val remoteProducts = storeAPI.getAllProduct().map { it.mapToDomain(it) }
                    storeAppDatabase.storeDao.insertAllProducts(remoteProducts)
                } else {
                    emit(Resource.Error("No internet connection",emptyList<ProductEntity>()))
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

        val cacheProductsById = storeAppDatabase.storeDao.getProductsById(productId)

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

    override fun getCartItems() = flow {
        emit(Resource.Loading())
//        val remoteCarts = storeAPI.getCarts(1).let { it.mapToDomain(it).products }
//        storeAppDatabase.storeDao.insertAllCarts(remoteCarts)
//        Log.i("Get Carts", "getCartItems: ${storeAppDatabase.storeDao.getAllCarts()}")

        val cacheCarts = storeAppDatabase.storeDao.getCartItems()

        if (cacheCarts?.isEmpty() == true) {
            try {
                val remoteCarts = storeAPI.getCarts(1).let { it.mapToDomain(it).products }
                storeAppDatabase.storeDao.insertAllCarts(remoteCarts)
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        "Oops,something went wrong!",
                        emptyList<GeneralProductAndCartProduct>()
                    )
                )
            } catch (e: IOException) {
                Resource.Error(
                    "Couldn't reach the server.Check your internet connection",
                    emptyList<GeneralProductAndCartProduct>()
                )
            }
        }

        val cacheCartItems = storeAppDatabase.storeDao.getCartItems()?.filter { it.cart != null }
        if (cacheCartItems?.isNotEmpty() == true) {
            emit(Resource.Success(cacheCartItems))
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