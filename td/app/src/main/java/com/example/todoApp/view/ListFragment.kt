package com.example.todoApp.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todoApp.adapter.CardAdapter
import com.example.todoApp.databinding.ListLayoutBinding
import com.example.todoApp.model.Swipe
import com.example.todoApp.model.Todo
import com.example.todoApp.model.Todor
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel

class ListFragment : Fragment() {

    private var _binding: ListLayoutBinding? =null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,
            TodoRepo(context?.applicationContext as Application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ListLayoutBinding.inflate(layoutInflater, container, false).also{
_binding = it  }.root


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.bar2("All")
        MainActivity.enableBottomNav(true)




        viewModel.todoSet.observe(viewLifecycleOwner)
        {
            Log.d("checker",it.toString())
            binding.rvLsit.adapter = CardAdapter(::selectedTodo)
            (binding.rvLsit.adapter as CardAdapter).updateUrls(it)
            var touch = ItemTouchHelper(Swipe(binding.rvLsit.adapter as CardAdapter))
            touch.attachToRecyclerView(binding.rvLsit)

        }

        binding.fab.setOnClickListener(){
            val controller = findNavController()
            val action = ListFragmentDirections.toComp()
            controller.navigate(action)
            Log.d("clicked","button")
        }

    }

    fun selectedTodo(todo: Todo){
        val controller = findNavController()
        val action = ListFragmentDirections.toDetails(todo)
        controller.navigate(action)
        Log.d("clicked",todo.toString())
    }
}