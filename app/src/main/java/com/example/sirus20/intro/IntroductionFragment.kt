package com.example.sirus20.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment() {
    private lateinit var binding: FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_introduction,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigate()
    }

    /*
    * fun for navigation
    * */
    private fun navigate() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(IntroductionFragmentDirections.actionIntroductionFragment2ToLoginFragment())
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(IntroductionFragmentDirections.actionIntroductionFragment2ToSignUPFragment())

        }
    }
}