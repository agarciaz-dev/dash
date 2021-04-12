package com.eelseth.domain.di

import com.eelseth.domain.repository.RestaurantRepository
import com.eelseth.domain.repository.RestaurantRepositoryImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class DomainModule : Module() {

    init {
        bind<RestaurantRepository>().toClass<RestaurantRepositoryImpl>()

    }
}