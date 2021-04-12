package com.eelseth.network.services

import com.eelseth.network.model.NTStoreFeed
import retrofit2.Response
import retrofit2.http.GET

internal interface RestaurantService {

    /**
     * Parameters hardcoded for testing
     * */
    @GET("store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50")
    suspend fun storeFeed(): Response<NTStoreFeed>

}