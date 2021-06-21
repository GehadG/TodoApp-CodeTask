package com.nordpass.todo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope

import androidx.paging.ExperimentalPagingApi
import com.nordpass.todo.R
import com.nordpass.todo.TODO_ITEM
import com.nordpass.todo.networking.models.ToDoResponse
import com.nordpass.todo.ui.recyclerviews.ToDoClickListener
import com.nordpass.todo.ui.recyclerviews.TodoPagingAdapter
import com.nordpass.todo.ui.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoActivity : AppCompatActivity() {
    private val todoViewModel: TodoViewModel by viewModels()
    private lateinit var adapter: TodoPagingAdapter
    private var fetchJob: Job? = null

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)


        adapter =
            TodoPagingAdapter(ToDoClickListener { todoItem -> onItemClick(todoItem) })
        todosRV.adapter = adapter

        setupObservers()
    }

    private fun onItemClick(todoItem: ToDoResponse.Data) {
        val fragment = DetailsFragment()
        val arguments = Bundle()
        arguments.putParcelable(TODO_ITEM, todoItem)
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            ).addToBackStack(null)
            .commit()
    }

    @ExperimentalPagingApi
    private fun setupObservers() {
        fetchJob?.cancel()
        fetchJob = lifecycleScope.launch {
            todoViewModel.fetchTodos().observe(this@ToDoActivity, Observer {
                adapter.submitData(this@ToDoActivity.lifecycle, it)
            })
        }
    }


}