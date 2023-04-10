package com.example.sirus20.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.dashboard.adapter.CategoriesAdapter
import com.example.sirus20.dashboard.adapter.FeaturedAdapter
import com.example.sirus20.dashboard.adapter.MostViewedAdapter
import com.example.sirus20.dashboard.modals.CategoryData
import com.example.sirus20.dashboard.modals.FeaturedData
import com.example.sirus20.dashboard.modals.MostViewedData
import com.example.sirus20.databinding.FragmentDashBoardBinding

class DashBoardFragment : Fragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentDashBoardBinding
    private lateinit var fetAdapter: FeaturedAdapter
    private lateinit var mostAdapter: MostViewedAdapter
    private lateinit var catAdapter: CategoriesAdapter

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
        categoryAdapter()
        mostViewedAdapter()
        featuredAdapter()
        openDrawer()
    }


    /*
    * fun to open drawer
    * */
    private fun openDrawer() {
        val mDrawer = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.imgMenu.setOnClickListener {
            mDrawer?.openDrawer(GravityCompat.START)
        }
        binding.imgPlus.setOnClickListener {
            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToIntroductionFragment2())
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
        catAdapter = CategoriesAdapter(catData)
        binding.categoriesRecycler.adapter = catAdapter

    }

    /*
    * setting adapter for most viewed data
    * */
    private fun mostViewedAdapter() {
        val mostData: ArrayList<MostViewedData> = ArrayList()
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostData.add(
            MostViewedData(
                R.drawable.mac,
                "MacDonald's",
                3.2f,
                "hello welcome to MacDonald's hello welcome to MacDonald's hello welcome to MacDonald's "
            )
        )
        mostAdapter = MostViewedAdapter(mostData)
        binding.mostViewedRecycler.adapter = mostAdapter
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
}