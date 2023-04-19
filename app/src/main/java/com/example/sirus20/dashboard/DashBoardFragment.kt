package com.example.sirus20.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.addplace.model.PlaceDataModel
import com.example.sirus20.dashboard.adapter.CategoriesAdapter
import com.example.sirus20.dashboard.adapter.FeaturedAdapter
import com.example.sirus20.dashboard.adapter.MostViewedAdapter
import com.example.sirus20.dashboard.listner.OnCellClicked
import com.example.sirus20.dashboard.modals.CategoryData
import com.example.sirus20.dashboard.modals.FeaturedData
import com.example.sirus20.databinding.FragmentDashBoardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashBoardFragment : Fragment(), OnCellClicked {

    /*
    * variables
    * */
    private lateinit var binding: FragmentDashBoardBinding
    private lateinit var fetAdapter: FeaturedAdapter
    private lateinit var mostAdapter: MostViewedAdapter
    private lateinit var catAdapter: CategoriesAdapter
    private var featuredDataList = ArrayList<PlaceDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_dash_board,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        featuredDataList.clear()
        retrievingData()
        categoryAdapter()
        featuredAdapter()
        openDrawer()
        setNavigate()
    }

    private fun setNavigate() {
        binding.layoutSearch.setOnClickListener {
            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToSearchFragment())
        }
        binding.imgPlus.setOnClickListener {
            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToChatFragment())
        }
    }


    /*
    * fun to open drawer
    * */
    private fun openDrawer() {
        val mDrawer = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.imgMenu.setOnClickListener {
            mDrawer?.openDrawer(GravityCompat.START)
        }

    }

    /*
    * setting adapter for category data
    * */
    private fun categoryAdapter() {
        val catData: ArrayList<CategoryData> = ArrayList()
        catData.add(CategoryData("Education", R.drawable.mac))
        catData.add(CategoryData("Shops", R.drawable.mac))
        catData.add(CategoryData("Medical", R.drawable.mac))
        catData.add(CategoryData("Hospital", R.drawable.mac))
        catData.add(CategoryData("Transport", R.drawable.mac))
        catData.add(CategoryData("Garage", R.drawable.mac))
        catAdapter = CategoriesAdapter(catData)
        binding.categoriesRecycler.adapter = catAdapter

    }


    /*
    * setting adapter for featured data
    * */
    private fun featuredAdapter() {
        val featuredData: ArrayList<FeaturedData> = ArrayList()
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        featuredData.add(
            FeaturedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        fetAdapter = FeaturedAdapter(featuredData)
        binding.featuredRecycler.adapter = fetAdapter
    }

    private fun retrievingData() {
        val db = Firebase.firestore
        //getting data from firebase fire store
        db.collection("PlaceDetails").get().addOnSuccessListener {
                documents ->
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
            mostAdapter = MostViewedAdapter(this, featuredDataList)
            binding.mostViewedRecycler.adapter = mostAdapter
            Log.d("FeaturedList", "retrievingData: $featuredDataList")
        }
    }

    /*
    * getting data from recycler view
    * */
    override fun onClick(placeDataModel: PlaceDataModel) {
        val bundle = Bundle()
        bundle.putParcelable("DATA", placeDataModel)
        findNavController().navigate(R.id.action_dashBoardFragment_to_detailsFragment, bundle)
    }


}