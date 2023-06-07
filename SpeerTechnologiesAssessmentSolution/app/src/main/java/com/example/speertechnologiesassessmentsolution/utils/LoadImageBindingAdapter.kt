package com.example.speertechnologiesassessmentsolution.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.speertechnologiesassessmentsolution.R
import de.hdodenhof.circleimageview.CircleImageView

class LoadImageBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["thumbnail", "error"], requireAll = false)
        fun loadImage(view: CircleImageView, image: String?) {
            if (!image.isNullOrEmpty()) {
                Glide.with(view.context)
                    .setDefaultRequestOptions(
                        RequestOptions()
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder))
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view)
            }
        }

    }
}