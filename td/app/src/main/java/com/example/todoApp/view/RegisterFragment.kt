package com.example.todoApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoApp.databinding.RegisterLayoutBinding
import com.example.todoApp.viewmodel.SyncViewModel

class RegisterFragment : Fragment() {

    private var _binding : RegisterLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = RegisterLayoutBinding.inflate(layoutInflater,container,false).also{_binding = it
    }.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReg.setOnClickListener(){


           // SyncViewModel.credentials =
            val controller = findNavController()
            val action = RegisterFragmentDirections.regToList()
            controller.navigate(action)
        }
    }
}