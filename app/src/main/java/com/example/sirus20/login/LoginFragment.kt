package com.example.sirus20.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.BaseFragment
import com.example.sirus20.common.ResponseHandler
import com.example.sirus20.common.Validation
import com.example.sirus20.databinding.FragmentLoginBinding
import com.example.sirus20.login.viewmodel.LoginViewModel


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_login,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        setNavigation()
        binding.btnLoginPage.setOnClickListener {
            checkValidation()
        }
    }

    /*
    * setting up navigation
    * */
    private fun setNavigation() {
        binding.txtCreateAnAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUPFragment())
        }
        binding.btnLoginBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * checking validation for email and password
    * */
    private fun checkValidation() {
        if (!Validation.isNotNull(binding.edtSignUpName.text.toString().trim())) {
            showSnackBar(getString(R.string.lbl_enter_email))
        } else if (!Validation.isEmailValid(binding.edtSignUpName.text.toString().trim())) {
            showSnackBar(getString(R.string.lbl_enter_valid_email))
        } else if (!Validation.isNotNull(binding.edtLoginPassword.text.toString().trim())) {
            showSnackBar(getString(R.string.lbl_enter_password))
        } else {
            getLoginData()
        }
    }

    /*
    * init view model
    * */

    private fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return viewModel
    }

    /*
    * getting login email & password of user
    * */
    private fun getLoginData() {
        viewModel.authenticateUser(
            binding.edtSignUpName.text.toString().trim(),
            binding.edtLoginPassword.text.toString().trim()
        )
        setObserver()
    }


    /*
    * setting up observer
    * */
    private fun setObserver() {
        viewModel.response.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("SignUpFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    Log.d("SignUpFragment", "setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Failed to Login.", Toast.LENGTH_SHORT)
                        .show()
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Log.d("SignUpFragment", "setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashBoardFragment())
                }
            }
        })
    }
}