package com.eelseth.restaurantapp.presentation.image

import androidx.annotation.DrawableRes

sealed class ImageSource {
    data class Resource(@DrawableRes val imageResource: Int) : ImageSource()
    data class Url(val imageUrl: String) : ImageSource()
}