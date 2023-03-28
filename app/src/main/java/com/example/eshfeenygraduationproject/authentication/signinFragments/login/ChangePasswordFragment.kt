package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.ChangePassword
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModelFactory
import com.example.eshfeenygraduationproject.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private var binding: FragmentChangePasswordBinding? = null
    private val args: ChangePasswordFragmentArgs by navArgs()
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater)

        val repository = UserRepoImpl()
        val viewModelFactory = SharedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        binding?.confBtn?.setOnClickListener {
            val newPassword = ChangePassword(binding?.newPasswordEditText?.text.toString())
            val newPasswordConf = ChangePassword(binding?.confNewPasswordEditText?.text.toString())
            Log.i("password", newPassword.toString())
            Log.i("password", "$newPasswordConf confpassword")

            if (newPassword.password == newPasswordConf.password) {
                viewModel.updateUserPassword(args.userId, newPassword)
                Log.i("password", "password is changed")
                Toast.makeText(requireContext(), "Password is changed", Toast.LENGTH_SHORT).show()
            } else {
                binding?.passwordNotMatch?.visibility = View.VISIBLE
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}