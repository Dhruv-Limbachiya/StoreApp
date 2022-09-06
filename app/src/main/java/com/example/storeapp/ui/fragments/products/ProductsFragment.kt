package com.example.storeapp.ui.fragments.products

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.storeapp.R
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.databinding.FragmentProductsBinding
import com.example.storeapp.ui.adapter.ProductsAdapter
import com.example.storeapp.ui.base.BaseFragment
import com.example.storeapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentProductsBinding
    private val mViewModel by viewModels<ProductsViewModel>()

    @Inject
    lateinit var mAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProductsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        collectFlows()
    }

    private fun initComponents() {
        mViewModel.getAllProducts()
        mBinding.etSearch.doAfterTextChanged {
            it?.let {
                if (it.isNotBlank()) {
                    mViewModel.getProductsByCategoryOrId(it.toString())
                } else {
                    mViewModel.getAllProducts()
                    mBinding.rvProducts.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.productState.collect {
                    if (it.isLoading) {
                        showProgressbar()
                    } else if (it.products?.isNotEmpty() == true) {
                        hideProgressbar()
                        addDataToRecyclerView(it.products ?: emptyList())
                        showRecyclerViewHideNoRecordFound()
                    } else if (it.errorMessage?.isNotEmpty() == true) {
                        hideProgressbar()
                        hideRecyclerViewShowNoRecordFound()
                        mBinding.root.showSnackBar(
                            it.errorMessage ?: getString(R.string.message_something_went_wrong),
                            true
                        )
                    }
                }
            }
        }
    }

    /**
     * Adds products in the recyclerview adapter.
     */
    private fun addDataToRecyclerView(products: List<ProductEntity>) {
        mBinding.rvProducts.apply {
            adapter = mAdapter
            mAdapter.submitList(products)
        }
    }

    /**
     * Helper method to show/hide recycler view and no record found textview.
     */
    private fun hideRecyclerViewShowNoRecordFound() {
        mBinding.rvProducts.isVisible = false
        mBinding.tvNoRecordFound.isVisible = true
    }

    private fun showRecyclerViewHideNoRecordFound() {
        mBinding.rvProducts.isVisible = true
        mBinding.tvNoRecordFound.isVisible = false
    }
}