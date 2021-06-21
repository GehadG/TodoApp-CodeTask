package com.nordpass.todo.networking

import com.nordpass.todo.networking.models.ToDoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoService {

    @GET("/public-api/todos")
    suspend fun getTodos(
        @Query("page") page: Int?
    ): ToDoResponse
}
