package com.nordpass.todo.ui.recyclerviews

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nordpass.todo.databinding.TodoItemBinding
import com.nordpass.todo.networking.models.ToDoResponse

class TodoPagingAdapter(private val cl: ToDoClickListener) :
    PagingDataAdapter<ToDoResponse.Data, TodoPagingAdapter.TodoViewholder>(
        TodoDiffCallback()
    ) {


    override fun onBindViewHolder(holder: TodoViewholder, position: Int) {

        val data = getItem(position)

        holder.bind(data, cl.clickListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewholder {

        return TodoViewholder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class TodoViewholder(
        private val binding: TodoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ToDoResponse.Data?, clickListener: (ToDoResponse.Data) -> Unit) {
            binding.let {
                it.root.setOnClickListener {
                    data?.let { it1 -> clickListener(it1) }
                }
                it.title.text = data?.title
                if (data != null) {
                    if (data.completed) it.leftColor.setBackgroundColor(Color.parseColor("#FF018786")) else it.leftColor.setBackgroundColor(
                        Color.parseColor("#32364f")
                    )
                }
            }

        }
    }

    private class TodoDiffCallback : DiffUtil.ItemCallback<ToDoResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: ToDoResponse.Data,
            newItem: ToDoResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ToDoResponse.Data,
            newItem: ToDoResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

}