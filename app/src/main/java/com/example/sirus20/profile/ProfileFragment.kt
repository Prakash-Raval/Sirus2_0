package com.example.sirus20.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.common.BaseFragment
import com.example.sirus20.common.ImagePicker.getFileName
import com.example.sirus20.common.ResponseHandler
import com.example.sirus20.common.Validation
import com.example.sirus20.databinding.FragmentProfileBinding
import com.example.sirus20.extension.setLocalImage
import com.example.sirus20.profile.viewmodel.UpdateDataViewModel
import com.example.sirus20.signup.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ProfileFragment : BaseFragment() {
    /*
    * variables
    * */
    private lateinit var binding: FragmentProfileBinding
    private var profileImagePath = ""
    private val auth = FirebaseAuth.getInstance()
    val id = auth.currentUser?.uid
    private val fireStore = FirebaseFirestore.getInstance()
    private lateinit var viewModel: UpdateDataViewModel
    private lateinit var uri: Uri
    private var password = ""
    private var imageUrl = ""

    /*
    * selecting image from gallery
    * */
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                binding.imgProfile.setLocalImage(imageUri, true)
                binding.imgProfile.background = null
                addImageToStorage(imageUri)
                this.uri = imageUri
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

        viewModel = getViewModel()
        imagePicker()
        getUpdateData()
        updateData()
    }

    /*
    * update data button click
    * */
    private fun updateData() {
        binding.btnUpdate.setOnClickListener {
            checkUpdateValidation()
        }
    }


    /*
    * adding image to fire base storage
    * */
    private fun addImageToStorage(uri: Uri) {
        val storageRef = Firebase.storage.reference
        val sd = getFileName(requireContext(), uri)
        val uploadTask = storageRef.child("upload/$sd").putFile(uri)
        uploadTask.addOnSuccessListener {
            storageRef.child("upload/$sd").downloadUrl.addOnSuccessListener {

                profileImagePath = it.toString()
                Log.d("TAG", "addImageToStorage: ${it.toString()}")

            }.addOnFailureListener {

            }
        }.addOnFailureListener {

        }
    }


    /*
    * getting view model
    * */
    private fun getViewModel(): UpdateDataViewModel {
        viewModel = ViewModelProvider(this)[UpdateDataViewModel::class.java]
        return viewModel
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
    * fun to check validation
    * */
    private fun checkUpdateValidation() {

        when {
            !Validation.isNotNull(binding.edtSignUpName.text.toString().trim()) -> {
                showSnackBar(getString(R.string.lbl_enter_name))
            }
            !Validation.isNotNull(binding.edtSignUpUserName.text.toString().trim()) -> {
                showSnackBar(getString(R.string.lbl_enter_usen_name))
            }
            else -> {

                var gender = "Male"
                binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById(checkedId) as RadioButton
                    gender = radioButton.text.toString()
                }

                if (profileImagePath == ""){
                    profileImagePath = imageUrl
                }
                else{
                    addImageToStorage(uri)
                }
                val sh = activity?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                val token = sh?.getString("TOKEN","")
                    viewModel.updateUser(
                        SignUpModel(
                            name = binding.edtSignUpName.text.toString().trim(),
                            email = binding.edtSignUpUserEmail.text.toString().trim(),
                            mobile = binding.edtSignUpUserMobile.text.toString().trim(),
                            userName = binding.edtSignUpUserName.text.toString().trim(),
                            day = binding.datePicker.dayOfMonth.toLong(),
                            month = binding.datePicker.month.toLong(),
                            year = binding.datePicker.year.toLong(),
                            gender = gender,
                            countryCode = binding.countryCodePicker.selectedCountryCode,
                            image = profileImagePath,
                            uid = id.toString(),
                            token = token,
                            password = password
                        ), id.toString()
                    )
                setObserver()
            }
        }
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
                    Log.d("AddPlaceFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    Log.d("AddPlaceFragment", "setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Failed to update data.", Toast.LENGTH_SHORT)
                        .show()
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Log.d("AddPlaceFragment", "setObserverData: $state")
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        "Data updated successfully.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        })
    }

    /*
    * getting data of user's profile
    * */
    private fun getUpdateData() {


        fireStore
            .collection("USER")
            .document(id.toString())
            .get()
            .addOnSuccessListener { document ->
                password = (document.get("password") as String?).toString()
                imageUrl = (document.get("image") as String?).toString()
                binding.data = SignUpModel(
                    name = document.get("name") as String?,
                    email = document.get("email") as String?,
                    mobile = document.get("mobile") as String?,
                    userName = document.get("userName") as String?,
                    gender = document.get("gender") as String?,
                    countryCode = document.get("countryCode") as String?,
                    image = document.get("image") as String?
                )

                /*
                * setting up date in date picker
                * */
                val day = document.get("day") as Long?
                val month = document.get("month") as Long?
                val year = document.get("year") as Long?

                year?.toInt()?.let {
                    month?.toInt()
                        ?.let { it1 ->
                            day?.toInt()
                                ?.let { it2 -> binding.datePicker.init(it, it1, it2, null) }
                        }
                }
                Toast.makeText(
                    requireContext(),
                    "Successfully getting data",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Unable to get data",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}


