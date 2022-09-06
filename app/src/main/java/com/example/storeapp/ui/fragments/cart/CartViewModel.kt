package com.example.storeapp.ui.fragments.cart

import androidx.lifecycle.ViewModel
import com.example.storeapp.repository.StoreAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeAppRepository: StoreAppRepository
) : ViewModel() {
}