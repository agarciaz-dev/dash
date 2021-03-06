package com.eelseth.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eelseth.persistence.dao.RestaurantDao
import com.eelseth.persistence.dao.RestaurantSaveDao
import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantSaved

@Database(
    entities = [
        DBRestaurant::class,
        DBRestaurantSaved::class
    ],
    version = 1
)

internal abstract class AppDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao
    abstract fun restaurantSaveDao(): RestaurantSaveDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "rr_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}