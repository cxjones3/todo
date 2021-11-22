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
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(){

    private var _binding : LoginLayoutBinding? = null
    val binding get() = _binding
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,
            TodoRepo(context?.applicationContext as Application)
        )
    }

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

        MainActivity.bar2("Login")

        binding?.logUser?.setText("username : cj2")
        binding?.logPassword?.setText("password : pass")
        binding?.btnLogin?.setOnClickListener(){

            SyncViewModel.credentials = LoginBody(binding?.logUser?.editableText.toString(),
                binding?.logPassword?.editableText.toString())

            logger()

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

    fun logger(){
        GlobalScope.launch(Dispatchers.IO) {val afterLog = LoginRepo.login(SyncViewModel.credentials)
            Log.d("testing login",afterLog.toString())
            Snackbar.make(requireView(), afterLog.body()!!.message, Snackbar.LENGTH_LONG).show()
            if (afterLog.message().equals("OK")) {
                LoginRepo.username = SyncViewModel.credentials.username
                LoginRepo.checkCredentials(activity?.application!!, afterLog.body()!!.token)
                withContext(Dispatchers.Main){ move() }
            }
            else
                binding?.logUser?.error  = "bad login"
        }
    }






}