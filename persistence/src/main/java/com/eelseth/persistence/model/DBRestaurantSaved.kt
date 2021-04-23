package com.eelseth.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eelseth.persistence.base.BaseDatabaseEntity

@Entity(
    tableName = "restaurant_saved",

    )

data class DBRestaurantSaved(
    @PrimaryKey @ColumnInfo(name = "restaurant_id") override val id: String,
    @ColumnInfo(name = "saved") val saved: Boolean = false,
) : BaseDatabaseEntity