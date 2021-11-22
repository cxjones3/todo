package com.example.todoApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoApp.databinding.DetialsLayoutBinding

class DetailsFragment : Fragment() {

    private var _binding : DetialsLayoutBinding? = null
    private val binding get() = _binding!!
    var args = navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DetialsLayoutBinding.inflate(layoutInflater,container,false)
        .also { _binding = it }.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newTodo = args.value.selection
        binding.tvTitle.text = newTodo.title
        binding.tvDesc.text = newTodo.description
        binding.switcher.isChecked = newTodo.completed
        binding.tvDate.text = newTodo.date.toString()
        binding.tvUpdate.text = newTodo.updatedAt.toString()
        binding.tvId.text = newTodo.id.toString()

        binding.btnEdit.setOnClickListener(){
            val controller = findNavController()
            val action = DetailsFragmentDirections.toEdits(newTodo)
            controller.navigate(action)
        }

        binding.btnReturn.setOnClickListener(){
            val controller = findNavController()
            val action = DetailsFragmentDirections.returnToList()
            controller.navigate(action)
        }
    }
}