package com.example.todoApp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoApp.databinding.ItemLayoutBinding
import com.example.todoApp.model.Todo
import com.example.todoApp.model.Todor
import com.example.todoApp.util.layoutInflater
import com.example.todoApp.viewmodel.SyncViewModel

class CardAdapter (
private val selectedTodo : (Todo) -> Unit
): RecyclerView.Adapter<CardAdapter.CardViewHolder> (){

    private val todos = mutableListOf<Todor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder.getInstance(parent).apply { itemView.setOnClickListener(){
            selectedTodo(SyncViewModel.convertFrom(todos[adapterPosition]))
        } }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.loadList(todos[position])
    }

    override fun getItemCount(): Int = todos.size

    fun updateUrls(list: List<Todor>){
        val size = this.todos.size
        this.todos.clear()
        //clear()
          notifyItemRangeRemoved(0,size)

        this.todos.addAll(list)
          notifyItemRangeInserted(0, list.size)
    }

    class CardViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun loadList(current : Todor){
            binding.tvTitle.text = current.title
            binding.tvDesc.text = current.description
        }
        companion object{
            fun getInstance(parent: ViewGroup)= ItemLayoutBinding.inflate(
                parent.layoutInflater, parent, false).run { CardViewHolder(this) }

        }
    }
}