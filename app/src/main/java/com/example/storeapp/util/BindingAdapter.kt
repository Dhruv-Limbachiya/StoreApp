package com.example.storeapp.util

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@BindingAdapter(value = ["setImageUrl", "setPlaceholder"], requireAll = false)
@SuppressLint("CheckResult")
fun AppCompatImageView.setImageUrl(imageUrl: String, placeHolder: Drawable) {

    val requestOptions = RequestOptions().apply {
        placeholder(placeHolder)
        error(placeHolder)
        diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .into(this)
}

