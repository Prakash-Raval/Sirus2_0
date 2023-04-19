package com.example.sirus20.signup

import android.content.Context.MODE_PRIVATE
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
import com.example.sirus20.databinding.FragmentSignUPBinding
import com.example.sirus20.signup.model.SignUpModel
import com.example.sirus20.signup.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth


class SignUPFragment : BaseFragment() {
    /*
    * variables
    * */
    private lateinit var binding: FragmentSignUPBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_sign_u_p,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        setVisibility()
    }

    /*
    * init view model
    * */
    private fun getViewModel(): SignUpViewModel {
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        return viewModel
    }

    /*
    * managing layout visibility and validation
    * */
    private fun setVisibility() {
        binding.btnNext1.setOnClickListener {

            if (!Validation.isNotNull(binding.edtSignUpName.text.toString().trim())) {
                showSnackBar(getString(R.string.lbl_enter_name))
            } else if (!Validation.isNotNull(binding.edtSignUpUserName.text.toString().trim())) {
                showSnackBar(getString(R.string.lbl_enter_usen_name))
            } else if (!Validation.isNotNull(binding.edtSignUpUserEmail.text.toString().trim())) {
                showSnackBar(getString(R.string.lbl_enter_email))
            } else if (!Validation.isEmailValid(
                    binding.edtSignUpUserEmail.text.toString().trim()
                )
            ) {
                showSnackBar(getString(R.string.lbl_enter_valid_email))
            } else if (!Validation.isNotNull(
                    binding.edtSignUpUserPassward.text.toString().trim()
                )
            ) {
                showSnackBar(getString(R.string.lbl_enter_password))
            } else if (!Validation.isValidPassword(
                    binding.edtSignUpUserPassward.text.toString().trim()
                )
            ) {
                showSnackBar(getString(R.string.lbl_enter_valid_password))
            } else {
                binding.parentLayout2.visibility = View.VISIBLE
                binding.parentLayout1.visibility = View.GONE
                binding.parentLayout3.visibility = View.GONE
            }
        }
        binding.datePicker.maxDate = System.currentTimeMillis()
        binding.btnNext2.setOnClickListener {
            binding.parentLayout2.visibility = View.GONE
            binding.parentLayout1.visibility = View.GONE
            binding.parentLayout3.visibility = View.VISIBLE
        }

        binding.btnNext3.setOnClickListener {
            if (!Validation.isNotNull(binding.edtSignUpUserMobile.text.toString().trim())) {
                showSnackBar(getString(R.string.lbl_enter_mobile))
            } else if (binding.edtSignUpUserMobile.text.toString().trim().length != 10) {
                showSnackBar(getString(R.string.lbl_enter_valid_mobile))
            } else {
                getFireBaseData()
                Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSignUpBack3.setOnClickListener {
            binding.parentLayout2.visibility = View.VISIBLE
            binding.parentLayout1.visibility = View.GONE
            binding.parentLayout3.visibility = View.GONE
        }

        binding.btnSignUpBack2.setOnClickListener {
            binding.parentLayout2.visibility = View.GONE
            binding.parentLayout1.visibility = View.VISIBLE
            binding.parentLayout3.visibility = View.GONE
        }
        binding.txtLoginClick.setOnClickListener {
            findNavController().navigate(SignUPFragmentDirections.actionSignUPFragmentToLoginFragment())
        }
        binding.btnSignUpBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * collecting request data for sign up
    * */
    private fun getFireBaseData() {
        val userData = SignUpModel()
        val sh = activity?.getSharedPreferences("MySharedPref", MODE_PRIVATE)

        userData.token = sh?.getString("TOKEN", "")
        userData.name = binding.edtSignUpName.text.toString().trim()
        userData.userName = binding.edtSignUpUserName.text.toString().trim()
        userData.email = binding.edtSignUpUserEmail.text.toString().trim()
        userData.password = binding.edtSignUpUserPassward.text.toString().trim()
        userData.gender = "Male"
        userData.day = binding.datePicker.dayOfMonth.toLong()
        userData.month = (binding.datePicker.month + 1).toLong()
        userData.year = binding.datePicker.year.toLong()
        userData.countryCode = binding.countryCodePicker.selectedCountryCode
        userData.mobile = binding.edtSignUpUserMobile.text.toString().trim()
        userData.uid = FirebaseAuth.getInstance().uid

        viewModel.getSignUpData(
            userData
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
                    Toast.makeText(requireContext(), "Failed to add data.", Toast.LENGTH_SHORT)
                        .show()
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Log.d("SignUpFragment", "setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(SignUPFragmentDirections.actionSignUPFragmentToLoginFragment())
                }
            }
        })
    }

}