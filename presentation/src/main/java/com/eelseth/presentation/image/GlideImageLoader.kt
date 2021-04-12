package com.eelseth.presentation.image

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eelseth.restaurantapp.presentation.image.ImageSource
import toothpick.InjectConstructor

@InjectConstructor
class GlideImageLoader(
    context: Context
) : ImageLoader {

    override fun loadImage(
        imageSource: ImageSource,
        view: ImageView,
        applyRoundedCorners: Boolean,
        applyCircle: Boolean,
        @DrawableRes errorDrawableResource: Int?,
        success: (() -> Unit)?,
        failure: (() -> Unit)?,
    ) {
        Glide.with(view.context)
            .load(resolveImageSource(imageSource))
            .apply {
                if (applyCircle) apply(RequestOptions.circleCropTransform())
                errorDrawableResource?.let { placeholder(errorDrawableResource) }
            }.into(view)
    }

    private fun resolveImageSource(imageSource: ImageSource): Any {
        return when (imageSource) {
            is ImageSource.Url -> imageSource.imageUrl
            is ImageSource.Resource -> imageSource.imageResource
        }
    }
}
