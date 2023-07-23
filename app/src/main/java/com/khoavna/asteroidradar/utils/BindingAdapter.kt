package com.khoavna.asteroidradar.utils

import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.squareup.picasso.Picasso

@BindingAdapter("app:loadImageOfDate")
fun loadImage(img: ImageView, apod: Apod) {
    if (apod.mediaType == "image") {
        Picasso.get().load(apod.url).into(img)
    }
}

@BindingAdapter("app:displayImageOfDate")
fun displayImage(view: View, apod: Apod) {
    if (apod.mediaType != "image") {
        view.visibility = GONE
    }
}