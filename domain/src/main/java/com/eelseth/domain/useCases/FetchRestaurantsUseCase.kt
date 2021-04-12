package com.eelseth.domain.useCases

import com.eelseth.core.model.Result
import com.eelseth.domain.repository.RestaurantRepository
import toothpick.InjectConstructor


@InjectConstructor
class FetchRestaurantsUseCase(
    private val restaurantRepository: RestaurantRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return restaurantRepository.fetchRestaurants()
    }

}