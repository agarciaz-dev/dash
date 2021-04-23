package com.eelseth.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.eelseth.persistence.base.BaseDao
import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantWithSaved
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class RestaurantDao : BaseDao<DBRestaurant>() {

    @Query(
        """
        SELECT r.* 
          FROM restaurant AS r
         INNER JOIN restaurant_saved AS s ON r.id = s.restaurant_id
         """
    )
    abstract fun restaurantsWithSaved(): Flow<List<DBRestaurantWithSaved>>

    @Query("SELECT * FROM restaurant ORDER BY name")
    abstract fun restaurants(): Flow<List<DBRestaurant>>

    @Query("UPDATE restaurant_saved SET saved = NOT saved WHERE restaurant_id = :restaurantId")
    abstract suspend fun toggleRestaurantSave(restaurantId: String)

    @Query("SELECT saved FROM restaurant_saved WHERE restaurant_id = :restaurantId")
    abstract suspend fun savedForRestaurant(restaurantId: String): Boolean
}
