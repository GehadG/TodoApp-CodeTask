package com.nordpass.todo.ui.recyclerviews

import com.nordpass.todo.networking.models.ToDoResponse

data class ToDoClickListener(val clickListener: (todoItem: ToDoResponse.Data) -> Unit)
