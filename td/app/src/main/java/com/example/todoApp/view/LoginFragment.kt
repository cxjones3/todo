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
import com.example.todoApp.databinding.LoginLayoutBinding
import com.example.todoApp.model.LoginBody
import com.example.todoApp.model.RegisterBody
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.viewmodel.SyncViewModel
import androidx.lifecycle.*
import com.example.todoApp.repo.TodoRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher

class LoginFragment : Fragment(){

    private var _binding : LoginLayoutBinding? = null
    val binding get() = _binding
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,
            TodoRepo(context?.applicationContext as Application)
        )
    }
    //private val viewModel by activityViewModels<SyncViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View=LoginLayoutBinding.inflate(inflater,container,false).also { _binding = it}.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.logUser?.setText("username : cj2")
        binding?.logPassword?.setText("password : pass")
        binding?.btnLogin?.setOnClickListener(){
         //   val controller = findNavController()
        //    val action = LoginFragmentDirections.toApp()
        //    controller.navigate(action)

            //testRepo()

            val reg2 = LoginBody(binding?.logUser?.editableText.toString(),
                binding?.logPassword?.editableText.toString())
                //LoginBody("username : cj2","password : pass")
            //viewModel.test(reg2)
           // LoginRepo.checkCredentials()
            SyncViewModel.credentials = reg2
           // viewModel.
            viewModel.viewLogin()

            // /*viewModel.createTodo.observe(viewLifecycleOwner){
            Snackbar.make(view, ComposeFragment.messenger, Snackbar.LENGTH_LONG).show()
            if(ComposeFragment.messenger.equals("lOK"))move()
            else
            binding?.logUser?.error  = "bad login"


           // val test = LoginRepo.register(registerBody)
        }

        binding?.btnReg?.setOnClickListener(){
            val controller = findNavController()
            val action = LoginFragmentDirections.toReg()
            controller.navigate(action)
        }

    }

    fun move() {
        val controller = findNavController()
        val action = LoginFragmentDirections.toList()
        controller.navigate(action)
    }






}