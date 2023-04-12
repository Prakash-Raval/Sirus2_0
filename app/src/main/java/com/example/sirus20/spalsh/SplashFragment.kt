package com.example.sirus20.spalsh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentSplashBinding
import com.example.sirus20.firebase.FireStoreClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_splash,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnim()
        lifecycleScope.launch {
            delay(3000)
            loadFragment()
        }
    }

    /*
    * loading up fragment
    * */
    private fun loadFragment() {
        val currentUser = FireStoreClass().getCurrentUserID()
        if (currentUser.isNotEmpty()) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToDashBoardFragment())
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment())
        }
    }

    /*
    * setting up animation
    * */
    private fun setupAnim() {
        val side = AnimationUtils.loadAnimation(requireContext(), R.anim.side_anim)
        val bottom = AnimationUtils.loadAnimation(requireContext(), R.anim.bootm_anim)
        binding.imgLogo.animation = side
        binding.txtSlogan.animation = bottom
    }
}