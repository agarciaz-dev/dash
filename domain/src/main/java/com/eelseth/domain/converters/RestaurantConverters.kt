package com.eelseth.domain.converters

import com.eelseth.core.utils.toDateTimestamp
import com.eelseth.domain.base.SERVER_DATE_FORMAT
import com.eelseth.domain.model.Restaurant
import com.eelseth.network.model.NTRestaurant
import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantWithSaved

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

internal fun DBRestaurantWithSaved.toRestaurant() = Restaurant(
    id = restaurant.id,
    name = restaurant.name,
    description = restaurant.description,
    coverImageUrl = restaurant.coverImageUrl,
    nextCloseTimestamp = restaurant.nextCloseTimestamp,
    nextOpenTimestamp = restaurant.nextOpenTimestamp,
    saved = dbRestaurantSaved.saved
)