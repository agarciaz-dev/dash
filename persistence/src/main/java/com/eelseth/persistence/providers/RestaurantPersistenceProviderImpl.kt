package com.eelseth.persistence.providers

import android.util.Log
import com.eelseth.persistence.AppDatabase
import com.eelseth.persistence.base.databaseOperation
import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantSaved
import com.eelseth.persistence.model.DBRestaurantWithSaved
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

    override fun restaurants(): Flow<List<DBRestaurantWithSaved>> =
        appDatabase.restaurantDao().restaurantsWithSaved().distinctUntilChanged()

    override suspend fun syncRestaurants(dbRestaurants: List<DBRestaurant>) {
        databaseOperation {
            appDatabase.restaurantDao().syncItems(
                newItems = dbRestaurants,
                storedItems = appDatabase.restaurantDao().restaurants().first()
            )
        }
    }

    override suspend fun insertRestaurantSave(r: DBRestaurantSaved) {
        databaseOperation {
            appDatabase.restaurantSaveDao().insert(r)
        }
    }

    override suspend fun saveRestaurant(restaurantId: String) {
        databaseOperation {
            Log.i("events", "restaurantId: $restaurantId")
            appDatabase.restaurantDao().toggleRestaurantSave(restaurantId)
        }
    }
}