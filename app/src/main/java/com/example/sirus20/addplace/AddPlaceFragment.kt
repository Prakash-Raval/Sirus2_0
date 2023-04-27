package com.example.sirus20.addplace

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.addplace.viewmodel.AddPlaceViewModel
import com.example.sirus20.common.BaseFragment
import com.example.sirus20.common.ImagePicker
import com.example.sirus20.common.ResponseHandler
import com.example.sirus20.databinding.FragmentAddPlaceBinding
import com.example.sirus20.extension.setLocalImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import timber.log.Timber


class AddPlaceFragment : BaseFragment() {
    /*
    * variables
    * */
    private lateinit var binding: FragmentAddPlaceBinding
    private lateinit var profileImagePath: String
    private lateinit var viewModel: AddPlaceViewModel

    /*
    * selecting image from gallery
    * */
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                binding.imgPlace.setLocalImage(imageUri, false)
                addImageToStorage(imageUri)
            }
        }


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()
        pickImage()
        callApiButton()
    }


    /*
    * adding image to fireBase Storage
    * */
    private fun addImageToStorage(uri: Uri) {
        val storageRef = Firebase.storage.reference
        //stored image name
        val sd = ImagePicker.getFileName(requireContext(), uri)
        Timber.d("Firebase addImageToStorage: $sd")

        //putting file to storage
        val uploadTask = storageRef.child("uploadPlace/$sd").putFile(uri)
        uploadTask.addOnSuccessListener {
            //download url from firebase
            storageRef.child("uploadPlace/$sd").downloadUrl.addOnSuccessListener {

                profileImagePath = it.toString()

            }.addOnFailureListener {
                Timber.d("TAG $it")
            }
        }.addOnFailureListener {
            Timber.d("TAG $it")

        }
    }


    /*
    * setting up toolbar
    * */
    private fun setToolBar() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * picking image from gallery
    * */
    private fun pickImage() {
        binding.imgPlace.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun callApiButton() {
        binding.btnSubmit.setOnClickListener {
            getFireBaseCall()
        }
    }

    /*
    * init view model
    * */
    private fun getViewModel(): AddPlaceViewModel {
        viewModel = ViewModelProvider(this)[AddPlaceViewModel::class.java]
        return viewModel
    }

    private fun getFireBaseCall() {
        viewModel = getViewModel()

        //request data to view model
        viewModel.addPlace(
            PlaceDataModel(
                placeName = binding.edtPlaceName.text.toString().trim(),
                placeImage = profileImagePath,
                placeCategory = binding.edtPlaceCategory.text.toString().trim(),
                placeDesc = binding.edtPlaceDesc.text.toString().trim(),
                placeRating = binding.edtPlaceRate.text.toString().toFloat(),
                placeType = binding.edtPlaceType.text.toString().trim(),
            )
        )


        /*
        *
        * observing data from view model
        * */
        viewModel.response.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Timber.d("AddPlaceFragment setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    Timber.d("AddPlaceFragment setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Failed to add data.", Toast.LENGTH_SHORT)
                        .show()
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Timber.d("AddPlaceFragment setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Data added Successfully.", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                }
            }
        })
    }
}