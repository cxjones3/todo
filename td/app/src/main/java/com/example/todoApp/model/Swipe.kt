package com.example.todoApp.model

import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoApp.adapter.CardAdapter
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel

class Swipe(var adapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition

        (adapter as CardAdapter).deleteItem(pos)
    }

}