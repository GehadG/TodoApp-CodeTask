package com.nordpass.todo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nordpass.todo.STARTING_PAGE_INDEX
import com.nordpass.todo.networking.TodoService
import com.nordpass.todo.networking.models.ToDoResponse
import retrofit2.HttpException
import java.io.IOException

class TodoDatasource(private val todoApi: TodoService) :
    PagingSource<Int, ToDoResponse.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDoResponse.Data> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = todoApi.getTodos(page)
            val todoList = response.data
            LoadResult.Page(
                data = todoList,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.meta.pagination.page == response.meta.pagination.pages) null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ToDoResponse.Data>): Int? {
        return state.anchorPosition
    }
}