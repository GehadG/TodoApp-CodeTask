package com.nordpass.todo.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nordpass.todo.STARTING_PAGE_INDEX
import com.nordpass.todo.networking.TodoService
import com.nordpass.todo.networking.models.ToDoResponse
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class TodoRemoteMediator(
    private val service: TodoService,
    private val db: TodoDatabase
) : RemoteMediator<Int, ToDoResponse.Data>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ToDoResponse.Data>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.todoDao.count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)

            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {

            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: STARTING_PAGE_INDEX
            val apiResponse = service.getTodos(page)

            val todosList = apiResponse.data

            val endOfPaginationReached =
                apiResponse.meta.pagination.page == apiResponse.meta.pagination.pages
            db.withTransaction {
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                db.paginationKeyDao.insertKey(
                    PaginationKey(
                        0,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.todoDao.insertAllTodos(todosList)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getKey(): PaginationKey? {
        return db.paginationKeyDao.getKeys().firstOrNull()
    }

}