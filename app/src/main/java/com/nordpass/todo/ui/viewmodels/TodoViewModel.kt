package com.nordpass.todo.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.nordpass.todo.networking.models.ToDoResponse
import com.nordpass.todo.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private var currentResult: LiveData<PagingData<ToDoResponse.Data>>? = null

    @ExperimentalPagingApi
    fun fetchTodos(): LiveData<PagingData<ToDoResponse.Data>> {
        val newResultLiveData: LiveData<PagingData<ToDoResponse.Data>> =
            repository.fetchTodos().cachedIn(viewModelScope)
        currentResult = newResultLiveData
        return newResultLiveData
    }


}