package com.eelseth.domain.useCases

import com.eelseth.domain.model.Restaurant
import com.eelseth.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import toothpick.InjectConstructor


@InjectConstructor
class RestaurantsUseCase(
    private val restaurantRepository: RestaurantRepository
) {

    operator fun invoke(): Flow<List<Restaurant>> {
        return restaurantRepository.restaurantsFlow()
    }

}