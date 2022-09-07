package com.example.storeapp.ui.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.cache.entity.CartItemEntity
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.repository.interfaces.CartRepository
import com.example.storeapp.util.Constants.DEFAULT_SHIPPING_CHARGE
import com.example.storeapp.util.ObservableString
import com.example.storeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartsRepository: CartRepository
) : ViewModel() {

    // Holds cart state as flow.
    private var _cartState = MutableStateFlow(CartState.INVALID_CART_STATE)
    val cartState: StateFlow<CartState> = _cartState

    // Holds checkout status as flow.
    private var _checkoutSuccess = MutableStateFlow(false)
    val checkoutSuccess: StateFlow<Boolean> = _checkoutSuccess

    // observables for views(xml)
    val observableSubTotal = ObservableString()
    val observableTotal = ObservableString()
    val observableShippingCharge = ObservableString()

    fun getCartItems() = viewModelScope.launch(Dispatchers.IO) {
        // Get cart items flow and compare each emission with their possible states.
        cartsRepository.getCartItems().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _cartState.value = _cartState.value.copy(isLoading = true) // made cart state as loading state.
                }
                is Resource.Success -> {
                    calculateTotal(res) // calculate products subtotal and total inc. shipping charges
                    _cartState.value = _cartState.value.copy(
                        carts = res.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _cartState.value =
                        _cartState.value.copy(isLoading = false, errorMessage = res.message)
                }
            }
        }.launchIn(this)
    }

    /**
     * Calculates products subtotal and total inc. shipping charges
     */
    private fun calculateTotal(res: Resource<List<GeneralProductAndCartProduct>>) {
        var subTotal = 0.0

        res.data?.forEach { generalProductAndCartProduct ->
            val product = generalProductAndCartProduct.productEntity ?: ProductEntity.INVALID_PRODUCT
            val cart = generalProductAndCartProduct.cart ?: CartItemEntity.INVALID_CART_ITEM

            if (cart.quantity != 0 && product.price != 0.0) {
                subTotal += product.price.times(cart.quantity)
            }
        }

        val total: Double = DEFAULT_SHIPPING_CHARGE + subTotal
        observableSubTotal.set(subTotal.toString())
        observableShippingCharge.set(DEFAULT_SHIPPING_CHARGE.toString())
        observableTotal.set(total.toString())
    }

    /**
     * Function to simulate checkout process by deleting cart items from the "carts" table.
     */
    fun onCheckOut() {
        viewModelScope.launch(Dispatchers.IO) {
            cartsRepository.deleteAllCartItems().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _cartState.value = _cartState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        calculateTotal(res)
                        _cartState.value = _cartState.value.copy(
                            carts = res.data ?: emptyList(),
                            isLoading = false
                        )
                        _checkoutSuccess.value = res.data?.isEmpty() == true
                    }
                    is Resource.Error -> {
                        _cartState.value =
                            _cartState.value.copy(isLoading = false, errorMessage = res.message)
                    }
                }
            }.launchIn(this)
        }
    }
}