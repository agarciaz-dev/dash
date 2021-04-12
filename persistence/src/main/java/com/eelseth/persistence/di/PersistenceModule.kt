package com.eelseth.persistence.di

import android.content.Context
import com.eelseth.persistence.AppDatabase
import com.eelseth.persistence.providers.RestaurantPersistenceProvider
import com.eelseth.persistence.providers.RestaurantPersistenceProviderImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class PersistenceModule(context: Context) : Module() {

    init {
        bind<AppDatabase>().toInstance(AppDatabase.getInstance(context))
        bind<RestaurantPersistenceProvider>().toClass<RestaurantPersistenceProviderImpl>()
    }
}