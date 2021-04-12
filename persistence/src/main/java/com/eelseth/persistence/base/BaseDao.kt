package com.eelseth.persistence.base

import androidx.room.*

internal abstract class BaseDao<T : BaseDatabaseEntity> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(item: T): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(items: List<T>): Int

    @Delete
    abstract suspend fun delete(item: T): Int

    @Delete
    abstract suspend fun delete(items: List<T>): Int

    @Transaction
    open suspend fun syncItem(item: T) {
        val id = insert(item)
        if (id == -1L) update(item)
    }

    @Transaction
    open suspend fun syncItems(items: List<T>) {
        val insertResults = insert(items)
        val updateList = items.filterIndexed { index, _ ->
            insertResults[index] == -1L
        }

        if (updateList.isNotEmpty()) update(updateList)
    }

    @Transaction
    open suspend fun syncItems(newItems: List<T>, storedItems: List<T>) {
        val newItemsIds = newItems.map { it.id }.toSet()
        val itemsToDelete = storedItems.filter { !newItemsIds.contains(it.id) }

        syncItems(newItems)
        delete(itemsToDelete)
    }
}
