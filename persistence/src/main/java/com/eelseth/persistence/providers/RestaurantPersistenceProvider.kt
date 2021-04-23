package com.eelseth.persistence.providers

import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantSaved
import com.eelseth.persistence.model.DBRestaurantWithSaved
import kotlinx.coroutines.flow.Flow

interface RestaurantPersistenceProvider {

    fun restaurants(): Flow<List<DBRestaurantWithSaved>>

    suspend fun syncRestaurants(dbRestaurants: List<DBRestaurant>)
    suspend fun saveRestaurant(restaurantId: String)

    suspend fun insertRestaurantSave(r: DBRestaurantSaved)
}