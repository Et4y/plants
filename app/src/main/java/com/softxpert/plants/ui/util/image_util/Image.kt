package com.softxpert.plants.ui.util.image_util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.softxpert.plants.R


fun ImageView.loadImageFromUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().override(200, 200))
        .error(R.drawable.ic_no_image)
        .transform(CenterCrop(), RoundedCorners(16)) // Combine CenterCrop and RoundedCorners
        .into(this)
}