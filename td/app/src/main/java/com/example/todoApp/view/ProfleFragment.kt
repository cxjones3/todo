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
import com.example.todoApp.databinding.ProfileLayoutBinding
import com.example.todoApp.repo.LoginRepo
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.viewmodel.SyncViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfleFragment : Fragment() {

    private var _binding : ProfileLayoutBinding? = null
    private  val binding get() = _binding!!
    private val viewModel by activityViewModels<SyncViewModel>(){
        SyncViewModel.Factory(context?.applicationContext as Application,
            TodoRepo(context?.applicationContext as Application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ProfileLayoutBinding.inflate(layoutInflater,container,false).also {
        _binding = it}.root

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.bar2("Profile")

        binding.username.setText(LoginRepo.username ?: "not logged in yet")
        binding.btnLogout.setOnClickListener(){
            GlobalScope.launch { LoginRepo.checkCredentials(activity?.application!!,"delete") }
            val controller = findNavController()
            controller.navigate(ProfleFragmentDirections.logout())
        }

        binding.btnSync.setOnClickListener(){
            viewModel.syncData()
        }
    }
}