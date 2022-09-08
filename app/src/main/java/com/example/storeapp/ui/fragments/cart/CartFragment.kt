package com.example.storeapp.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.storeapp.R
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.databinding.FragmentCartBinding
import com.example.storeapp.ui.adapter.CartsAdapter
import com.example.storeapp.ui.base.BaseFragment
import com.example.storeapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private lateinit var mBinding: FragmentCartBinding
    private val mViewModel by viewModels<CartViewModel>()

    @Inject
    lateinit var mAdapter: CartsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCartBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        collectFlows()
    }

    private fun initComponents() {
        mViewModel.getCartItems()
    }

    private fun collectFlows() {
        lifecycleScope.launchWhenStarted {
            mViewModel.cartState.collect {
                if (it.isLoading) {
                    showProgressbar()
                } else if (it.carts?.isNotEmpty() == true) {
                    hideProgressbar()
                    addDataToRecyclerView(it.carts ?: emptyList())
                    showRecyclerViewHideNoRecordFound()
                } else if (it.errorMessage?.isNotEmpty() == true) {
                    hideProgressbar()
                    hideRecyclerViewShowNoRecordFound()
                    mBinding.root.showSnackBar(
                        it.errorMessage ?: getString(R.string.message_something_went_wrong),
                        true
                    )
                } else {
                    hideProgressbar()
                    addDataToRecyclerView(it.carts ?: emptyList())
                    if (it.carts?.isEmpty() == true) {
                        hideRecyclerViewShowNoRecordFound()
                    } else {
                        showRecyclerViewHideNoRecordFound()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.checkoutState.collectLatest { uiState ->
                mBinding.layoutPricingDetails.isVisible = !uiState.isSuccess
                uiState.message?.let {
                    mBinding.root.showSnackBar(
                        it,
                        false
                    )
                }
            }
        }
    }

    /**
     * Adds products in the recyclerview adapter.
     */
    private fun addDataToRecyclerView(carts: List<GeneralProductAndCartProduct>) {
        mBinding.rvCartItems.apply {
            adapter = mAdapter
            mAdapter.submitList(carts)
        }
    }

    /**
     * Helper method to show/hide recycler view and no record found textview.
     */
    private fun hideRecyclerViewShowNoRecordFound() {
        mBinding.rvCartItems.isVisible = false
        mBinding.tvNoCartItemFound.isVisible = true
    }

    private fun showRecyclerViewHideNoRecordFound() {
        mBinding.rvCartItems.isVisible = true
        mBinding.tvNoCartItemFound.isVisible = false
    }
}