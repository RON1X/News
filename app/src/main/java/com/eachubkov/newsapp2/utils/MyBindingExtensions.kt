package com.eachubkov.newsapp2.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

@BindingAdapter("loadRoundedImageFromUrl")
fun ImageView.loadImageFromUrlWithRounded(imageUrl: String?) {
    this.load(imageUrl) {
        crossfade(true)
        crossfade(500)
        transformations(RoundedCornersTransformation(24f))

    }
    if (imageUrl.isNullOrEmpty()) this.gone()
}

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl(imageUrl: String?) {
    this.load(imageUrl) {
        crossfade(true)
        crossfade(500)
    }
    if (imageUrl.isNullOrEmpty()) this.gone()
}

@BindingAdapter("setVisibilityIfEmpty")
fun View.setVisibilityIfEmpty(data: String?) {
    if (data.isNullOrEmpty()) this.gone()
}