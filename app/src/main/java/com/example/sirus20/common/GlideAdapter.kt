package com.example.sirus20.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("image", "placeholder")
fun setImage(image: ImageView, url: String?, placeHolder: Drawable) {
    if (!url.isNullOrEmpty()) {
        Glide.with(image.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(image)
    } else {
        image.setImageDrawable(placeHolder)
    }

}