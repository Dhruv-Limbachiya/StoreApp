package com.example.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.data.cache.entity.GeneralProductAndCartProduct
import com.example.storeapp.databinding.LayoutCartItemBinding

/**
 * Created by Dhruv Limbachiya on 29-10-2021.
 */
class CartsAdapter :
    ListAdapter<GeneralProductAndCartProduct, CartsAdapter.CartsViewHolder>(CartsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartsViewHolder {
        return CartsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CartsViewHolder, position: Int) {
        val cart = getItem(position) // current cart.
        holder.bind(cart)
    }

    class CartsViewHolder(private val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views with product data.
         */
        fun bind(
            cart: GeneralProductAndCartProduct?,
        ) {
            cart?.let {
                binding.cartItem = it // assigns cart in binding cart variable.
            }
        }

        companion object {
            fun from(parent: ViewGroup): CartsViewHolder {
                return CartsViewHolder(
                    LayoutCartItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

}

class CartsDiffCallback : DiffUtil.ItemCallback<GeneralProductAndCartProduct>() {
    override fun areItemsTheSame(
        oldItem: GeneralProductAndCartProduct,
        newItem: GeneralProductAndCartProduct
    ) =
        oldItem.productEntity?.id == newItem.productEntity?.id

    override fun areContentsTheSame(
        oldItem: GeneralProductAndCartProduct,
        newItem: GeneralProductAndCartProduct
    ) =
        oldItem.productEntity?.id == newItem.productEntity?.id
}