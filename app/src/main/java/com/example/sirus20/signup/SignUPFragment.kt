package com.example.sirus20.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentSignUPBinding

class SignUPFragment : Fragment() {
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
    * managing layout visibility
    * */
    private fun setVisibility() {
        binding.btnNext1.setOnClickListener {
            binding.parentLayout2.visibility = View.VISIBLE
            binding.parentLayout1.visibility = View.GONE
            binding.parentLayout3.visibility = View.GONE
        }
        binding.btnNext2.setOnClickListener {
            binding.parentLayout2.visibility = View.GONE
            binding.parentLayout1.visibility = View.GONE
            binding.parentLayout3.visibility = View.VISIBLE
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
        binding.btnSignUpBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}