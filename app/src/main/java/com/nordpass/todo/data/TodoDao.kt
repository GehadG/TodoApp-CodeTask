package com.nordpass.todo.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nordpass.todo.networking.models.ToDoResponse

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(list: List<ToDoResponse.Data>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: ToDoResponse.Data)

    @Query("SELECT * FROM todos_table")
    fun getTodos(): PagingSource<Int, ToDoResponse.Data>

    @Query("DELETE FROM todos_table")
    suspend fun clearTodos()

    @Query("SELECT COUNT(id) from todos_table")
    suspend fun count(): Int

    @Query("SELECT * FROM todos_table WHERE id = :id")
    suspend fun findTodoById(id: Int): ToDoResponse.Data?

    @Query("SELECT * from todos_table")
    suspend fun getAllTodos(): List<ToDoResponse.Data>

}