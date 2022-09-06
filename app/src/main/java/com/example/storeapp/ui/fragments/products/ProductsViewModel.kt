package com.example.storeapp.ui.fragments.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.repository.StoreAppRepository
import com.example.storeapp.util.Resource
import com.example.storeapp.util.isNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val storeAppRepository: StoreAppRepository
) : ViewModel() {

    private var _productState = MutableStateFlow(ProductState.INVALID_PRODUCT_STATE)
    val productState: StateFlow<ProductState> = _productState

    fun getAllProducts() = viewModelScope.launch(Dispatchers.IO) {
        storeAppRepository.getAllProducts().onEach { res ->
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
                        _productState.value.copy(isLoading = false, errorMessage = res.message)
                }
            }
        }.launchIn(this)
    }

    fun getProductsByCategoryOrId(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        if (searchQuery.isNumber()) {
            storeAppRepository.getProductsById(searchQuery.toInt()).onEach { res ->
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
            storeAppRepository.getProductsByCategory(searchQuery).onEach { res ->
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