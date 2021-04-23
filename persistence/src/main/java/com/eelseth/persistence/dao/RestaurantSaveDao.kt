package com.eelseth.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.eelseth.persistence.base.BaseDao
import com.eelseth.persistence.model.DBRestaurant
import com.eelseth.persistence.model.DBRestaurantSaved
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class RestaurantSaveDao : BaseDao<DBRestaurantSaved>() {

    @Query("SELECT * FROM restaurant_saved WHERE restaurant_id =:restaurantId LIMIT 1")
    abstract fun restaurantSave(restaurantId: String): DBRestaurantSaved


}
