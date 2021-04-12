package com.eelseth.domain.utils

import com.eelseth.domain.model.Restaurant
import java.util.*

fun Restaurant.isOpen(): Boolean {
    val currentTime = Calendar.getInstance().timeInMillis
    return if (nextOpenTimestamp != null && nextCloseTimestamp != null) {
        nextOpenTimestamp <= currentTime && currentTime <= nextCloseTimestamp
    } else false
}