package com.eelseth.network.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NTRestaurant(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "cover_img_url") val coverImageUrl: String,
    @field:Json(name = "next_close_time") val nextCloseTimeDate: String,
    @field:Json(name = "next_open_time") val nextOpenTimeDate: String,
)