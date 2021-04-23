package com.eelseth.domain.useCases

import android.util.Log
import com.eelseth.core.model.Result
import com.eelseth.domain.repository.RestaurantRepository
import toothpick.InjectConstructor


@InjectConstructor
class SaveRestaurantUseCase(
    private val restaurantRepository: RestaurantRepository
) {

    suspend operator fun invoke(restaurantId: String): Result<Unit> {
        Log.i("events", "SaveRestaurantUseCase: $restaurantId")
        return restaurantRepository.saveRestaurant(restaurantId)
    }

}