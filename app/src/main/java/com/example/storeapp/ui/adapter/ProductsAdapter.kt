package com.example.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.data.cache.entity.ProductEntity
import com.example.storeapp.databinding.LayoutProductsItemBinding

/**
 * Created by Dhruv Limbachiya on 29-10-2021.
 */
class ProductsAdapter: ListAdapter<ProductEntity, ProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) // current product.
        holder.bind(product)
    }

    class ProductViewHolder(private val binding: LayoutProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views with product data.
         */
        fun bind(
            product: ProductEntity?,
        ) {
            product?.let {
                binding.product = it // assigns product in binding product variable.
            }
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                return ProductViewHolder(
                    LayoutProductsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity) =
        oldItem.id == newItem.id
}