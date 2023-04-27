package com.example.sirus20.user

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.DialogCategoryBinding
import com.example.sirus20.databinding.DialogPriceBinding
import com.example.sirus20.databinding.FragmentUserBinding
import com.example.sirus20.user.adapter.Adapter
import com.example.sirus20.user.listner.OnProductClick
import com.example.sirus20.user.model.ProductModel
import com.example.sirus20.user.model.Rating
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import org.json.JSONArray
import timber.log.Timber


class UserFragment : Fragment(), OnProductClick {

    private lateinit var binding: FragmentUserBinding
    private lateinit var adapter: Adapter
    private var list = ArrayList<ProductModel>()
    private var priceFilter: String = ""
    private var categoryFilter: String = "All"
    private var chipID: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_user,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        addListData()
        setButtonClick()
    }


    /*
    * setting up adapter
    * */
    private fun setAdapter() {
        adapter = Adapter(list, this)
        binding.recyclerView.adapter = adapter
    }

    /*
    * adding data to list
    * */
    private fun addListData() {

        val facilityJsonArray = JSONArray(loadJSONFromAssets())
        for (i in 0 until facilityJsonArray.length()) {
            val productModel = ProductModel()
            val facilityJSONObject = facilityJsonArray.getJSONObject(i)
            productModel.id = facilityJSONObject.getInt("id")
            productModel.title = facilityJSONObject.getString("title")
            productModel.category = facilityJSONObject.getString("category")
            productModel.description = facilityJSONObject.getString("description")
            productModel.price = facilityJSONObject.getDouble("price")
            productModel.image = facilityJSONObject.getString("image")
            productModel.rating =
                Gson().fromJson(facilityJSONObject.get("rating").toString(), Rating::class.java)
            list.add(productModel)
        }
        Timber.d("TAG addListData: $list")


    }

    private fun loadJSONFromAssets(): String {
        return resources.openRawResource(R.raw.data).bufferedReader().use { reader ->
            reader.readText()
        }
    }

    private fun setButtonClick() {
        binding.apply {
            fab.setOnClickListener { fab.isExpanded = !fab.isExpanded }
            fabCategory.setOnClickListener {
                categoryDialog()

            }
            fabPrice.setOnClickListener { priceDialog() }
        }

    }


    private fun categoryDialog() {
        val builder = BottomSheetDialog(requireContext())
        val bind: DialogCategoryBinding =
            DialogCategoryBinding.inflate(LayoutInflater.from(requireContext()))
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*
        * chip group click
        * */
        for (i in 0 until bind.chipGroupFilter.childCount) {
            if ((bind.chipGroupFilter.getChildAt(i) as Chip).text == categoryFilter) {
                (bind.chipGroupFilter.getChildAt(i) as Chip).isChecked = true
            }
        }

        bind.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            Timber.d("TAG2 categoryDialog: $checkedId")
            chipID = group.checkedChipId
            list.clear()
            addListData()
            val chip: Chip? = group.findViewById(checkedId)

            chip?.let { chipView ->
                categoryFilter = chipView.text.toString()
                filterAllData()
            }
            builder.dismiss()
        }
        builder.setContentView(bind.root)
        builder.setCanceledOnTouchOutside(true)
        builder.show()

    }

    private fun priceDialog() {
        val builder = BottomSheetDialog(requireContext())
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind: DialogPriceBinding =
            DialogPriceBinding.inflate(LayoutInflater.from(requireContext()))
        when (priceFilter) {
            "Low To High" -> bind.chip2.isChecked = true
            "High To Low" -> bind.chip1.isChecked = true
        }
        bind.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->

            list.clear()
            addListData()
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let { chipView ->
                priceFilter = chipView.text.toString()
                filterAllData()
            }
            builder.dismiss()
        }
        builder.setContentView(bind.root)
        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterAllData() {
        list.clear()
        addListData()

        if (priceFilter.isNotEmpty()) {
            when (priceFilter) {
                "Low To High" -> {
                    list.sortBy {
                        it.price
                    }
                }
                "High To Low" -> {
                    list.sortByDescending {
                        it.price
                    }
                }
                else -> {
                    list.clear()
                    addListData()
                }
            }
        }

        if (categoryFilter.isNotEmpty()) {
            if (categoryFilter != "All") {

                list.retainAll {
                    it.category == categoryFilter
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onClick(productModel: ProductModel) {
        findNavController().navigate(
            R.id.action_userFragment_to_userDetailFragment,
            Bundle().apply {
                putParcelable("PRODUCT_MODEL", productModel)
            })
    }
}