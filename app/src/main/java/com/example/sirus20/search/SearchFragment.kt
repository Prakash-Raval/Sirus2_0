package com.example.sirus20.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.dashboard.adapter.MostViewedAdapter
import com.example.sirus20.dashboard.listner.OnCellClicked
import com.example.sirus20.databinding.FragmentSearchBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment(), OnCellClicked {

    /*
    * variables
    * */
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MostViewedAdapter
    private var featuredDataList = ArrayList<PlaceDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_search,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        retrievingData()
        applySearch()
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
    * setting data to recycler view
    * */
    private fun retrievingData() {
        val db = Firebase.firestore
        //getting data from firebase fire store
        db.collection("PlaceDetails").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document["placeName"].toString()
                val desc = document["placeDesc"].toString()
                val rate = document["placeRating"].toString().toFloat()
                val image = document["placeImage"].toString()
                val type = document["placeType"].toString()
                val category = document["placeCategory"].toString()

                //adding data to list from firebase
                featuredDataList += listOf(
                    PlaceDataModel(
                        placeName = name,
                        placeDesc = desc,
                        placeType = type,
                        placeRating = rate,
                        placeCategory = category,
                        placeImage = image,
                        placeId = document.id
                    )
                )
            }
            adapter = MostViewedAdapter(this, featuredDataList)
            binding.rvSearch.adapter = adapter
            Log.d("FeaturedList", "retrievingData: $featuredDataList")
        }
    }

    /*
    * search view
    * */
    private fun applySearch() {

        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                Log.d("TAG44", "onQueryTextSubmit: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                Log.d("TAG45", "onQueryTextSubmit: $newText")
                return true
            }
        })

    }

    override fun onClick(placeDataModel: PlaceDataModel) {
    }

}