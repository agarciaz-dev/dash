package com.eelseth.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.eelseth.persistence.base.BaseDao
import com.eelseth.persistence.model.DBRestaurant
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class RestaurantDao : BaseDao<DBRestaurant>() {

    @Query("SELECT * FROM restaurant ORDER BY name")
    abstract fun restaurants(): Flow<List<DBRestaurant>>


}
