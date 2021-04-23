package com.eelseth.domain.repository

import com.eelseth.core.dispatcher.DispatcherProvider
import com.eelseth.core.model.Result
import com.eelseth.domain.converters.toDBRestaurant
import com.eelseth.domain.converters.toRestaurant
import com.eelseth.domain.model.Restaurant
import com.eelseth.network.base.ApiResult
import com.eelseth.network.providers.RestaurantServiceProvider
import com.eelseth.persistence.model.DBRestaurantSaved
import com.eelseth.persistence.providers.RestaurantPersistenceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton
@InjectConstructor
internal class RestaurantRepositoryImpl(
    private val restaurantServiceProvider: RestaurantServiceProvider,
    private val restaurantPersistenceProvider: RestaurantPersistenceProvider,
    private val dispatcherProvider: DispatcherProvider
) : RestaurantRepository {

    override fun restaurantsFlow(): Flow<List<Restaurant>> {
        return restaurantPersistenceProvider.restaurants().map { list ->
            list.map { it.toRestaurant() }
        }
    }

    override suspend fun fetchRestaurants(): Result<Unit> =
        withContext(dispatcherProvider.io) {
            when (val result = restaurantServiceProvider.restaurants()) {
                is ApiResult.Success -> {
                    restaurantPersistenceProvider.syncRestaurants(
                        result.data.map { it.toDBRestaurant() }
                    )

                    result.data.forEach {
                        restaurantPersistenceProvider.insertRestaurantSave(
                            DBRestaurantSaved(
                                id = it.id,
                                saved = false
                            )
                        )
                    }

                    Result.Success(Unit)
                }
                is ApiResult.Error -> Result.Error(result.throwable)
            }
        }

    override suspend fun saveRestaurant(restaurantId: String): Result<Unit> =
        withContext(dispatcherProvider.io) {
            restaurantPersistenceProvider.saveRestaurant(restaurantId)
            Result.Success(Unit)
        }
}