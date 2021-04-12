package com.eelseth.network.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NTStoreFeed(
    @field:Json(name = "stores") val stores: List<NTRestaurant>,
)