package com.example.sirus20.placedetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.databinding.FragmentDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore

class DetailsFragment : Fragment() {
    /*
    * variables
    * */
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mFirebaseDatabaseInstance: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
        setToolBar()
        getData()
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
    * getting data from next fragment
    * */
    private fun getData() {
        val args = this.arguments
        val data = args?.get("DATA") as PlaceDataModel
        binding.imgPlace.setImageURI(Uri.parse(data.placeImage))
        binding.data = data
        binding.imgDelete.setOnClickListener {
            deleteData(data)
        }
    }


    /*
    * deleting fire store data
    * */
    private fun deleteData(model: PlaceDataModel) {


        model.placeId?.let {
            mFirebaseDatabaseInstance
                .collection("PlaceDetails")
                .document(it)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Successfully deleted data",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()

                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Unable to delete data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }


}