package com.eelseth.presentation.image

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.eelseth.restaurantapp.presentation.image.ImageSource

interface ImageLoader {
    fun loadImage(
        imageSource: ImageSource,
        view: ImageView,
        applyRoundedCorners: Boolean = false,
        applyCircle: Boolean = false,
        @DrawableRes errorDrawableResource: Int? = null,
        success: (() -> Unit)? = null,
        failure: (() -> Unit)? = null,
    )
}