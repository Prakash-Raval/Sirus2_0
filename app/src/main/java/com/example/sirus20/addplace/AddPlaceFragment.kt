package com.example.sirus20.addplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentAddPlaceBinding


class AddPlaceFragment : Fragment() {

    private lateinit var binding: FragmentAddPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_add_place,
            container,
            false
        )
        return binding.root
    }

}