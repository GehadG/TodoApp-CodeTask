package com.nordpass.todo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nordpass.todo.data.TodoDao
import com.nordpass.todo.data.TodoDatabase
import com.nordpass.todo.networking.TodoService
import com.nordpass.todo.networking.models.ToDoResponse
import com.nordpass.todo.repositories.TodoRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class ComponentsTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: TodoDatabase
    private lateinit var todoDao: TodoDao
    @Inject
    lateinit var todoApi: TodoService

    @Inject
    lateinit var todoRepository: TodoRepository

    @Before
    fun setup() {
        hiltRule.inject()
        todoDao = database.todoDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testingRoomComponentInsert() = runBlockingTest {
        val todo = ToDoResponse.Data(
            id = 9999,
            title = "Test",
            completed = true,
            createdAt = "2012-02-12",
            updatedAt = "2012-02-12",
            userId = 383
        )
        todoDao.insertTodo(todo)
        val allTodos = todoDao.getAllTodos()
        assertThat(allTodos).contains(todo)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testingRoomComponentClear() = runBlockingTest {
        val todo = ToDoResponse.Data(
            id = 9999,
            title = "Test",
            completed = true,
            createdAt = "2012-02-12",
            updatedAt = "2012-02-12",
            userId = 383
        )
        todoDao.insertTodo(todo)
        todoDao.clearTodos()
        val count = todoDao.count()
        assertThat(count).isEqualTo(0)
    }

    @ExperimentalPagingApi
    @Test
    fun testingApiComponent() = runBlockingTest {
        GlobalScope.launch(Dispatchers.Main) {
            val testResults = todoApi.getTodos(1)
            assertThat(testResults.code).isEqualTo(200)
        }
    }

}