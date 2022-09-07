package com.example.storeapp.ui.fragments.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.repository.ProductsRepository
import com.example.storeapp.util.Resource
import com.example.storeapp.util.isNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private var _productState = MutableStateFlow(ProductState.INVALID_PRODUCT_STATE)
    val productState: StateFlow<ProductState> = _productState

    fun getAllProducts() = viewModelScope.launch(Dispatchers.IO) {
        productsRepository.getAllProducts().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _productState.value = _productState.value.copy(isLoading = true, products = res.data ?: emptyList())
                }
                is Resource.Success -> {
                    _productState.value = _productState.value.copy(
                        products = res.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _productState.value =
                        _productState.value.copy(isLoading = false, errorMessage = res.message)
                }
            }
        }.launchIn(this)
    }

    fun getProductsByCategoryOrId(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        if (searchQuery.isNumber()) {
            productsRepository.getProductsById(searchQuery.toInt()).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _productState.value = _productState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _productState.value = _productState.value.copy(
                            products = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _productState.value =
                            _productState.value.copy(
                                isLoading = false,
                                errorMessage = res.message,
                                products = res.data
                            )
                    }
                }
            }.launchIn(this)
        } else {
            productsRepository.getProductsByCategory(searchQuery).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _productState.value = _productState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _productState.value = _productState.value.copy(
                            products = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _productState.value =
                            _productState.value.copy(
                                isLoading = false,
                                errorMessage = res.message,
                                products = res.data
                            )
                    }
                }
            }.launchIn(this)
        }
    }
}