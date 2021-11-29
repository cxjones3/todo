package com.example.todoApp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoApp.databinding.RegisterLayoutBinding
import com.example.todoApp.model.LoginBody
import com.example.todoApp.model.RegisterBody
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.viewmodel.SyncViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        MainActivity.bar2("Register")
        binding.btnReg.setOnClickListener(){
            if(checkEmail(binding.logEmail.editableText.toString())){
                val rBody = RegisterBody(
                    binding.logUser.editableText.toString(),
                    binding.logEmail.editableText.toString(),
                    binding.logPassword.editableText.toString()
                )
                registerUser(rBody)
                SyncViewModel.credentials = LoginBody(binding.logUser.editableText.toString(),
                    binding.logPassword.editableText.toString())

            }
            else{
                Snackbar.make(requireView(), "invalid email", Snackbar.LENGTH_LONG).show()
                binding.logEmail.error  = "bad login"
            }

        }
    }
    fun checkEmail(email : String) : Boolean{
        if(!(email.contains("@"))) return false
        var sides = email.split("@")
        Log.d("sides",sides.toString())
        if(sides.size != 2) return false
        if(!(sides[1].contains(".com")) &&
            !(sides[1].contains(".net")) &&
            !(sides[1].contains(".org")) &&
            !(sides[1].contains(".me")) )return false
        if(sides[0].length < 1 || sides[1].length < 1)return false
        else return true
    }

    fun move(){
        val controller = findNavController()
        val action = RegisterFragmentDirections.regToList()
        controller.navigate(action)
    }

    fun registerUser(registerBody: RegisterBody){
        Log.d("testing register","top of method")
        GlobalScope.launch(Dispatchers.IO) {val afterLog = LoginRepo.register(registerBody)
            Log.d("testing register",afterLog.toString())
            Snackbar.make(requireView(), afterLog.message(), Snackbar.LENGTH_LONG).show()
            if (afterLog.message().equals("Created")) {


                LoginRepo.username = SyncViewModel.credentials.username
                LoginRepo.checkCredentials(activity?.application!!, afterLog.body()!!.token)
                withContext(Dispatchers.Main){ move() }
            }
          //  else
              //  binding?.logUser?.error  = "bad login"
        }.also{if(it.isCancelled)
            binding?.logUser?.error  = "bad login"
        }
    }
}