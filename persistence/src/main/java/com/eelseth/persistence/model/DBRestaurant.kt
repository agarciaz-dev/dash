package com.eelseth.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eelseth.persistence.base.BaseDatabaseEntity

@Entity(
    tableName = "restaurant"
)

data class DBRestaurant(
    @PrimaryKey @ColumnInfo(name = "id") override val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "cover_image_url") val coverImageUrl: String,
    @ColumnInfo(name = "next_close_timestamp") val nextCloseTimestamp: Long?,
    @ColumnInfo(name = "next_open_timestamp") val nextOpenTimestamp: Long?,
) : BaseDatabaseEntity