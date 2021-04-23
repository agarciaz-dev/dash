package com.eelseth.persistence.model

import androidx.room.Embedded
import androidx.room.Relation


data class DBRestaurantWithSaved(
    @Embedded
    val restaurant: DBRestaurant,
    @Relation(
        parentColumn = "id",
        entityColumn = "restaurant_id",
        entity = DBRestaurantSaved::class,
    )
    val dbRestaurantSaved: DBRestaurantSaved
)