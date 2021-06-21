package com.nordpass.todo.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.room.withTransaction
import com.nordpass.todo.data.TodoDatabase
import com.nordpass.todo.data.TodoRemoteMediator
import com.nordpass.todo.networking.TodoService
import com.nordpass.todo.networking.models.ToDoResponse
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(private val todoService: TodoService, private val db: TodoDatabase) {

    private val pagingSourceFactory = { db.todoDao.getTodos() }

    @ExperimentalPagingApi
    fun fetchTodos(): LiveData<PagingData<ToDoResponse.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = TodoRemoteMediator(
                todoService,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

}