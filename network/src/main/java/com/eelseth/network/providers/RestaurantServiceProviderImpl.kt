package com.eelseth.network.providers

import com.eelseth.network.base.ApiResult
import com.eelseth.network.base.apiCall
import com.eelseth.network.config.ServiceProvider
import com.eelseth.network.model.NTRestaurant
import com.eelseth.network.services.RestaurantService
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton
@InjectConstructor
internal class RestaurantServiceProviderImpl(
    private val serviceProvider: ServiceProvider
) : RestaurantServiceProvider {

    private val service by lazy {
        serviceProvider.retrofit.create(RestaurantService::class.java)
    }

    override suspend fun restaurants(): ApiResult<List<NTRestaurant>> {
        return when (val result = apiCall { service.storeFeed() }) {
            is ApiResult.Success -> ApiResult.Success(result.data.stores)
            is ApiResult.Error -> result
        }
    }

}