package com.example.storeapp.repository

import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.util.NetworkHelper
import com.example.storeapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 07-09-2022.
 */
class CartRepositoryImpl @Inject constructor(
    private val storeAPI: StoreAPI,
    private val storeAppDatabase: StoreAppDatabase,
    private val networkHelper: NetworkHelper
) : CartRepository {

    override fun getCartItems() = flow {
        emit(Resource.Loading())
        delay(500L)
        val cacheCarts = storeAppDatabase.storeDao.getAllCarts()

        if (cacheCarts?.isEmpty() == true) {
            try {
                if (networkHelper.isInternetConnected()) {
                    val remoteCarts = storeAPI.getCarts(1).let { it.mapToDomain(it).products }
                    storeAppDatabase.storeDao.insertAllCarts(remoteCarts)
                } else {
                    emit(
                        Resource.Error(
                            "No internet connection",
                            emptyList<GeneralProductAndCartProduct>()
                        )
                    )
                }
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

    override fun deleteAllCartItems() = flow {
        emit(Resource.Loading())
        delay(500L)
        storeAppDatabase.storeDao.deleteAllCartItems()
        val cacheCarts = storeAppDatabase.storeDao.getAllCarts()
        if (cacheCarts?.isEmpty() == true) {
            emit(
                Resource.Success(
                    emptyList<GeneralProductAndCartProduct>(),
                    "Order placed! Products will be delivered soon at doorstep"
                )
            )
        } else {
            emit(
                Resource.Error(
                    "Failed to delete cart items",
                    emptyList<GeneralProductAndCartProduct>()
                )
            )
        }
    }
}