package com.eelseth.domain.repository

import com.eelseth.core.model.Result
import com.eelseth.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {

    fun restaurantsFlow(): Flow<List<Restaurant>>

    suspend fun fetchRestaurants(): Result<Unit>
    suspend fun saveRestaurant(restaurantId: String): Result<Unit>

}