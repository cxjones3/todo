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
import androidx.navigation.fragment.navArgs
import com.example.todoApp.databinding.EditLayoutBinding
import com.example.todoApp.model.Todo
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragment : Fragment() {

    private var _binding : EditLayoutBinding? = null
    private val binding get() = _binding
    val args = navArgs<EditFragmentArgs>()
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,
            TodoRepo(context?.applicationContext as Application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = EditLayoutBinding.inflate(layoutInflater,container,false).also{
        _binding = it}.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.bar2("Edit")

        val newTodo = args.value.editable
        val controller = findNavController()
        binding?.tvTitle?.setText(newTodo.title)
        binding?.tvDesc?.setText(newTodo.description)
        binding?.switcher?.isEnabled =true
        binding?.switcher?.isChecked = newTodo.completed
        binding?.tvDate?.setText(newTodo.date.toString())
        binding?.tvUpdate?.setText(newTodo.updatedAt.toString())

        binding?.btnCancel?.setOnClickListener(){controller.navigateUp()}

        binding?.btnDel?.setOnClickListener(){
            val deletingTodo =args.value.editable
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(deletingTodo.title)
                    .setMessage("Are you sure you want to delete this todo??")
                    .setNegativeButton("no") { dialog, which -> }
                    .setPositiveButton("yes") { dialog, which ->
                        delete(deletingTodo)
                    }
                    .show()
            }
        }

        binding?.btnSave?.setOnClickListener(){
            val savedTodo = Todo(newTodo.id,
                binding?.tvTitle?.editableText.toString(),
                binding?.tvDesc?.editableText.toString(),
                binding?.switcher?.isChecked!!,
                 newTodo.userId,
                Integer.decode(binding?.tvDate?.editableText.toString()),
                Integer.decode(binding?.tvUpdate?.editableText.toString())
            )
            save(savedTodo)
        }
    }

    fun move(){
        val controller = findNavController()
        controller.navigate(EditFragmentDirections.afterDel())

    }

    fun delete(todo: Todo){
        GlobalScope.launch(Dispatchers.IO) {
            val afterLog = LoginRepo.delete(todo.id)
            Log.d("testing delete",afterLog.toString())
            Snackbar.make(requireView(), afterLog.message(), Snackbar.LENGTH_LONG).show()
            if (afterLog.message().equals("OK")) {
                viewModel.viewDel(todo)
                withContext(Dispatchers.Main){ move() }
            }
        }
    }

    fun save(todo: Todo){
        GlobalScope.launch(Dispatchers.IO) {
            val editResponse = LoginRepo.update(todo.id, todo)
            Log.d("testing delete",editResponse.toString())
            Snackbar.make(requireView(), editResponse.message(), Snackbar.LENGTH_LONG).show()
            if (editResponse.message().equals("OK")) {
                viewModel.viewEdit(todo)
                withContext(Dispatchers.Main){ move() }
            }
        }
    }

    companion object{
       var deleted : Boolean? = null
        var updated=  false
    }
}