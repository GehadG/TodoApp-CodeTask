package com.nordpass.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nordpass.todo.R
import com.nordpass.todo.TODO_ITEM
import com.nordpass.todo.networking.models.ToDoResponse
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getParcelable<ToDoResponse.Data>(TODO_ITEM)
        title.text = data?.title
        updated.text = data?.updatedAt
        if (data != null) {
            if (data.completed) {
                animationView.setAnimation(R.raw.todo_completed)
            } else {
                animationView.setAnimation(
                    R.raw.todo_pending
                )
                animationView.scale=1f
            }
            animationView.playAnimation()
        }

    }


}