package com.example.sirus20.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.ImagePicker
import com.example.sirus20.databinding.FragmentProfileBinding
import com.example.sirus20.extension.setLocalImage

class ProfileFragment : Fragment() {
    /*
    * variables
    * */
    private lateinit var binding: FragmentProfileBinding
    private var profileImagePath = ""

    /*
    * selecting image from gallery
    * */
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                binding.imgProfile.setLocalImage(imageUri, true)
                binding.imgProfile.background = null
                profileImagePath =
                    ImagePicker.getFileFromUri(imageUri, requireContext()).absolutePath

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_profile,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        imagePicker()
    }

    /*
    * setting up toolbar
    * */
    private fun setToolbar() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * pick image from gallery
    * */
    private fun imagePicker() {
        binding.imgProfile.setOnClickListener {
            getContent.launch("image/*")
        }
    }


    /*
    * updating user's data with profile picture
    * */
    private fun updateData() {

    }


}