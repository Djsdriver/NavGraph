package com.example.navgraph

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadPlaylist(anySource: Any) {
    Glide
        .with(this)
        .load(anySource)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}