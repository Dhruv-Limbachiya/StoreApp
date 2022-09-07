package com.example.storeapp.repository.implementation

import com.example.storeapp.data.cache.db.StoreAppDatabase
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.remote.StoreAPI
import com.example.storeapp.repository.interfaces.CartRepository
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
        emit(Resource.Loading()) // emit the loading state
        delay(500L) // simulate processing requests to make progressbar visible for certain amount of time
        val cacheCarts = storeAppDatabase.cartsDao.getAllCarts()

        if (cacheCarts?.isEmpty() == true) {
            try {
                // Fetch carts from the api if and only if there are no cart items in "carts" table
                if (networkHelper.isInternetConnected()) {
                    val remoteCarts = storeAPI.getCarts(1).let { it.mapToDomain(it).products }
                    storeAppDatabase.cartsDao.insertAllCarts(remoteCarts)
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

        /**
         * Get list of pair object (ProductEntity & CartItemEntity) and filter out the null cart item.
         */
        val cacheCartItems = storeAppDatabase.cartsDao.getCartItems()?.filter { it.cart != null }

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
        emit(Resource.Loading()) // emit the loading state
        delay(500L) // simulate processing requests to make progressbar visible for certain amount of time

        storeAppDatabase.cartsDao.deleteAllCartItems() // deletes all the cart items in "carts" table
        val cacheCarts = storeAppDatabase.cartsDao.getAllCarts()

        // Ensuring all the cart items are deleted or not
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