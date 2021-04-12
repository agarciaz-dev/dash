package com.eelseth.network.di

import com.eelseth.network.providers.RestaurantServiceProvider
import com.eelseth.network.providers.RestaurantServiceProviderImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NetworkModule : Module() {

    init {
        bind<RestaurantServiceProvider>().toClass<RestaurantServiceProviderImpl>()
    }

}