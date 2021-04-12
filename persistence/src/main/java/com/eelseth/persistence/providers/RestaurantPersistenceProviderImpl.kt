package com.eelseth.persistence.providers

import com.eelseth.persistence.AppDatabase
import com.eelseth.persistence.base.databaseOperation
import com.eelseth.persistence.model.DBRestaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton
@InjectConstructor
internal class RestaurantPersistenceProviderImpl(
    private val appDatabase: AppDatabase
) : RestaurantPersistenceProvider {

    override fun restaurants(): Flow<List<DBRestaurant>> =
        appDatabase.restaurantDao().restaurants().distinctUntilChanged()

    override suspend fun syncRestaurants(dbRestaurants: List<DBRestaurant>) {
        databaseOperation {
            appDatabase.restaurantDao().syncItems(
                newItems = dbRestaurants,
                storedItems = appDatabase.restaurantDao().restaurants().first()
            )
        }
    }
}