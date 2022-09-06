package com.example.storeapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.repository.StoreAppRepository
import com.example.storeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 06-09-2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeAppRepository: StoreAppRepository
) : ViewModel() {

    private var _productState = MutableStateFlow(mutableListOf<GeneralProductAndCartProduct>())
    val productState: StateFlow<List<GeneralProductAndCartProduct>> = _productState


    fun getAllProducts() = viewModelScope.launch(Dispatchers.IO) {
        storeAppRepository.getCartItems().onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _productState.value = res.data as MutableList<GeneralProductAndCartProduct>
                }
            }
        }.launchIn(this)
    }

}