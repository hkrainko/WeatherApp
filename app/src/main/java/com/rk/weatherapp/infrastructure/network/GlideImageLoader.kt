package com.rk.weatherapp.infrastructure.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideImageLoader {

    fun loadImage(ctx: Context, url: String?, imageView: ImageView) {
        if (url == null) {
            imageView.setImageDrawable(null)
        }
        Glide.with(ctx).load(url).into(imageView)
    }

}