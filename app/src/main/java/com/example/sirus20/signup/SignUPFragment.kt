package com.example.sirus20.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.BaseFragment
import com.example.sirus20.common.Validation
import com.example.sirus20.databinding.FragmentSignUPBinding

class SignUPFragment : BaseFragment() {
    private lateinit var binding: FragmentSignUPBinding

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
        setVisibility()
    }

    /*
    * managing layout visibility and validation
    * */
    private fun setVisibility() {
        binding.btnNext1.setOnClickListener {

            if (!Validation.isNotNull(binding.edtSignUpName.text.toString().trim())) {
                showSnackBar("Please enter name")
            } else if (!Validation.isNotNull(binding.edtSignUpUserName.text.toString().trim())) {
                showSnackBar("Please enter user name")
            } else if (!Validation.isNotNull(binding.edtSignUpUserEmail.text.toString().trim())) {
                showSnackBar("Please enter email")
            } else if (!Validation.isEmailValid(
                    binding.edtSignUpUserEmail.text.toString().trim()
                )
            ) {
                showSnackBar("Please enter valid email")
            } else if (!Validation.isNotNull(
                    binding.edtSignUpUserPassward.text.toString().trim()
                )
            ) {
                showSnackBar("Please enter password")
            } else if (!Validation.isValidPassword(
                    binding.edtSignUpUserPassward.text.toString().trim()
                )
            ) {
                showSnackBar("Please enter valid password")
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
                showSnackBar("Please enter mobile number")
            } else if (binding.edtSignUpUserMobile.text.toString().trim().length != 10) {
                showSnackBar("Please enter valid mobile number")
            } else {
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


}