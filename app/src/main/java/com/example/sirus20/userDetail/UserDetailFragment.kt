package com.example.sirus20.userDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentUserDetailBinding
import com.example.sirus20.user.model.ProductModel


class UserDetailFragment : Fragment() {
    /*
    variables
    * */
    private lateinit var binding: FragmentUserDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.fragment_user_detail,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setButtonClick()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        binding.userDetails.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.userDetails.txtToolbarHeader.setText(R.string.user_details)
    }

    /*
    * getting data
    * */
    private fun getData() {
        val args = this.arguments
        val productModel = args?.get("PRODUCT_MODEL") as ProductModel
        binding.data = productModel
    }

    private fun setButtonClick() {
        val args = this.arguments
        val productModel = args?.get("PRODUCT_MODEL") as ProductModel
        binding.btnMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_userDetailFragment_to_userInfoFragment,
                Bundle().apply {
                    putParcelable("Data", productModel)
                })
        }
    }

}