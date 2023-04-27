package com.example.sirus20.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentUserInfoBinding
import com.example.sirus20.user.model.ProductModel


class UserInfoFragment : Fragment() {

    private lateinit var binding: FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_user_info,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setUpToolbar()
    }

    private fun getData() {
        val args = this.arguments
        val productModel = args?.get("Data") as ProductModel
        binding.data = productModel
        binding.btnMore.setOnClickListener {
            it.visibility = View.GONE
            binding.txtDescription.visibility = View.VISIBLE
        }
    }

    private fun setUpToolbar() {
        binding.userInfo.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.userInfo.txtToolbarHeader.setText(R.string.user_info)
    }

}