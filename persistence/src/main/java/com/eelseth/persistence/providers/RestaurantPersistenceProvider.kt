package com.eelseth.persistence.providers

import com.eelseth.persistence.model.DBRestaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantPersistenceProvider {

    fun restaurants(): Flow<List<DBRestaurant>>

    suspend fun syncRestaurants(dbRestaurants: List<DBRestaurant>)
}