package com.example.storeapp.ui.activities

import androidx.lifecycle.ViewModel
import com.example.storeapp.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Dhruv Limbachiya on 06-09-2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

}