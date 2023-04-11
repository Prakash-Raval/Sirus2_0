package com.example.sirus20.common

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sirus20.MainActivity
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentBaseBinding
import com.example.sirus20.extension.hideKeyboard
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {
    private lateinit var binding: FragmentBaseBinding
    private lateinit var mProgressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_base,
            container,
            false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = Dialog(requireContext())
    }

    fun showProgressDialog() {
        mProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setContentView(R.layout.dialog_progressbar)
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
    }

    fun hideProgressBar() {
        mProgressDialog.dismiss()
    }

    /*
          * method to make snack bar
          * */
    fun showSnackBar(message: String) {
        hideKeyboard()
        val snackBar = Snackbar.make(
            (activity as MainActivity).findViewById(android.R.id.content)!!,
            message,
            Snackbar.LENGTH_SHORT
        )
        val view = snackBar.view
        val snackTV = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackTV.maxLines = 5
        snackBar.setBackgroundTint(Color.BLACK)
        snackBar.setTextColor(ContextCompat.getColor(requireContext(),R.color.status_bar_yellow))
        snackBar.show()
    }
}