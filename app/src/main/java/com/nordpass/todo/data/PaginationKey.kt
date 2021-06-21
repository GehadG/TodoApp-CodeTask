package com.nordpass.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagination_keys")
data class PaginationKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val isEndReached: Boolean
)