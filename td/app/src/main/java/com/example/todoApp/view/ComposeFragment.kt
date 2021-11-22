package com.example.todoApp.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoApp.databinding.ComposeLayoutBinding
import com.example.todoApp.model.Todo
import com.example.todoApp.model.TodoBody
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComposeFragment : Fragment() {

    private var _binding : ComposeLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,TodoRepo(context?.applicationContext as Application))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View=  ComposeLayoutBinding.inflate(layoutInflater,container,false).also {
        _binding = it}.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.bar2("Compose")
        binding?.btnSave?.setOnClickListener(){

           // SyncViewModel.create = TodoBody(binding.tvTitle.editableText.toString())
            add(TodoBody(binding.tvTitle.editableText.toString()))

           // /*viewModel.createTodo.observe(viewLifecycleOwner){
            //    Snackbar.make(view, messenger, Snackbar.LENGTH_LONG).show()
           // if(messenger.equals("aOK"))move()
               /* if(it.message().equals("OK")){
                    val controller = findNavController()
                    val action = ComposeFragmentDirections.afterComp()
                    controller.navigate(action)
                    Log.d("after save",it.body().toString())
                }
                else Snackbar.make(view, it.body().toString(), Snackbar.LENGTH_LONG).show()*/
            }
        binding?.btnCancel?.setOnClickListener(){move()}

        }

    fun move(){
        val controller = findNavController()
        val action = ComposeFragmentDirections.afterComp()
        controller.navigate(action)
    }

    fun add(create: TodoBody){
        GlobalScope.launch(Dispatchers.IO) {
            val createTodo = LoginRepo.add(create)
            Log.d("testing delete",createTodo.toString())
            Snackbar.make(requireView(), createTodo.message(), Snackbar.LENGTH_LONG).show()
            if (createTodo.message().equals("OK")) {
                viewModel.viewAdd(createTodo.body()!!)
                withContext(Dispatchers.Main){ move() }
            }
        }
    }

    companion object{
        var messenger = "no"
    }

}


