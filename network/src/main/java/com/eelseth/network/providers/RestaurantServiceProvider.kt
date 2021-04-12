package com.eelseth.network.providers

import com.eelseth.network.base.ApiResult
import com.eelseth.network.model.NTRestaurant

interface RestaurantServiceProvider {

    suspend fun restaurants(): ApiResult<List<NTRestaurant>>
}