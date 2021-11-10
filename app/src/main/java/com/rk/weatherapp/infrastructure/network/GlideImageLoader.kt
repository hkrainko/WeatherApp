package com.rk.weatherapp.infrastructure.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

object GlideImageLoader {

    fun loadImage(ctx: Context, url: String?, imageView: ImageView) {
        if (url == null) {
            imageView.setImageDrawable(null)
        }
       Glide.with(ctx).load(url).into(imageView)
//        Picasso.get().load(url).into(imageView)
    }

}