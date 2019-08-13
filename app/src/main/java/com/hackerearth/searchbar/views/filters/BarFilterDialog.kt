package com.hackerearth.searchbar.views.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.databinding.DialogBarFilterBinding
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.views.MainViewModel
import com.hackerearth.searchbar.views.filters.viewmodel.BarFilterViewModel

class BarFilterDialog : BottomSheetDialogFragment() {
    lateinit var viewModel: BarFilterViewModel
    lateinit var binding: DialogBarFilterBinding
    var mainViewModel: MainViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bar_filter, container, false)
        viewModel = ViewModelProviders.of(this).get(BarFilterViewModel::class.java)
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.triggerEventToView.observe(this, Observer {
            if (it != null) {
                if (it == Constants.SHOW_SNACKBAR) {
                    Toast.makeText(context, getString(R.string.error_make_selection), Toast.LENGTH_SHORT).show()
                } else if (it == Constants.CLOSE_FILTER) {
                    mainViewModel?.filterData?.value = viewModel.filterData
                    dismiss()
                }
            }
        })
    }

}