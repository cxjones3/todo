package com.example.todoApp.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoApp.R
import com.example.todoApp.databinding.EditLayoutBinding
import com.example.todoApp.model.Todo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

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
        val newTodo = args.value.editable
        val controller = findNavController()
        binding?.tvTitle?.setText(newTodo.title)
        binding?.tvDesc?.setText(newTodo.description)
        binding?.switcher?.isEnabled =true
        binding?.switcher?.isChecked = newTodo.completed
        binding?.tvDate?.setText(newTodo.date.toString())
        binding?.tvUpdate?.setText(newTodo.updatedAt.toString())
       // binding?.tvId?.setText(newTodo.id.toString())

        binding?.btnCancel?.setOnClickListener(){controller.navigateUp()}

        binding?.btnDel?.setOnClickListener(){
            val deletingTodo =args.value.editable
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(deletingTodo.title)
                    .setMessage("Are you sure you want to delete this todo??")
                    .setNegativeButton("no") { dialog, which -> }
                    .setPositiveButton("yes") { dialog, which ->
                        SyncViewModel.modTodo = deletingTodo
                        Log.d("test",SyncViewModel.modTodo.toString())
                        viewModel.viewDel()
                        Snackbar.make(view, ComposeFragment.messenger, Snackbar.LENGTH_LONG).show()
                        if(ComposeFragment.messenger.equals("dOK"))move()

                       /* viewModel.delTodo.observe(viewLifecycleOwner){
                            Snackbar.make(view, it.message(), Snackbar.LENGTH_LONG).show()
                            if(it.message().equals("aOK"))
                               move()
                            else Snackbar.make(view, it.body().toString(), Snackbar.LENGTH_LONG).show()
                        }*/
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

            SyncViewModel.modTodo = savedTodo
            viewModel.viewEdit()
            Snackbar.make(view, ComposeFragment.messenger, Snackbar.LENGTH_LONG).show().also {
                if(ComposeFragment.messenger.equals("uOK"))move()
            }


            /*viewModel.editTodo.observe(viewLifecycleOwner){
                Snackbar.make(view, it.message(), Snackbar.LENGTH_LONG).show()
                if(it.message().equals("OK")){
                    val action = EditFragmentDirections.backToDetails(savedTodo)
                    controller.navigate(action)
                    Log.d("after save",it.body().toString())
                }
                else Snackbar.make(view, it.body().toString(), Snackbar.LENGTH_LONG).show()
            }*/

        }
    }

    fun move(){
        val controller = findNavController()
        controller.navigate(EditFragmentDirections.afterDel())

    }

    companion object{
       var deleted : Boolean? = null
        var updated=  false
    }
}