package com.eelseth.domain.converters

import android.util.Log
import com.eelseth.core.utils.toDateTimestamp
import com.eelseth.domain.base.SERVER_DATE_FORMAT
import com.eelseth.domain.model.Restaurant
import com.eelseth.network.model.NTRestaurant
import com.eelseth.persistence.model.DBRestaurant

internal fun NTRestaurant.toDBRestaurant() = DBRestaurant(
    id = id,
    name = name,
    description = description,
    coverImageUrl = coverImageUrl,
    nextCloseTimestamp = nextCloseTimeDate.toDateTimestamp(SERVER_DATE_FORMAT),
    nextOpenTimestamp = nextOpenTimeDate.toDateTimestamp(SERVER_DATE_FORMAT).also {
        //Log.i("events", "$name - open:$it - ")
    },
)

internal fun DBRestaurant.toRestaurant() = Restaurant(
    id = id,
    name = name,
    description = description,
    coverImageUrl = coverImageUrl,
    nextCloseTimestamp = nextCloseTimestamp,
    nextOpenTimestamp = nextOpenTimestamp
)