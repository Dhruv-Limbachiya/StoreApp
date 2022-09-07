package com.example.storeapp.ui.base

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.storeapp.R

open class BaseFragment : Fragment() {

    private var mDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDialog = Dialog(requireActivity())
    }

    /**
    * Method to show progress bar dialog.
    */
    fun showProgressbar() {
        mDialog?.let {
            it.setContentView(R.layout.progressbar_dialog_layout)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    /**
     * Method to hide progress bar dialog.
     */
    fun hideProgressbar() {
        mDialog?.dismiss()
    }
}