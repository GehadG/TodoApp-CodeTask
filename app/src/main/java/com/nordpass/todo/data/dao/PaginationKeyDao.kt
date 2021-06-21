package com.nordpass.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nordpass.todo.data.PaginationKey

@Dao
interface PaginationKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PaginationKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: PaginationKey)

    @Query("DELETE FROM pagination_keys")
    suspend fun clearPaginationKeys()

    @Query("SELECT * FROM pagination_keys")
    suspend fun getKeys():List<PaginationKey>
}