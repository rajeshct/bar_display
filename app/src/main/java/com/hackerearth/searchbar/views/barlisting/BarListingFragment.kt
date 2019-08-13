package com.hackerearth.searchbar.views.barlisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.base.BaseFragment
import com.hackerearth.searchbar.databinding.FragmentBarListingBinding
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.views.MainViewModel
import com.hackerearth.searchbar.views.barlisting.viewmodel.BareListingViewModel


class BarListingFragment : BaseFragment() {
    override fun tagValue(): String {
        return Constants.TAG_BARLISTINGFRAGMENT
    }

    var mainViewModel: MainViewModel? = null
    lateinit var barListingViewModel: BareListingViewModel
    lateinit var binding: FragmentBarListingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bar_listing, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barListingViewModel = ViewModelProviders.of(this).get(BareListingViewModel::class.java)
        binding.viewModel = barListingViewModel
        binding.executePendingBindings()
        barListingViewModel.init()
        activity?.let { it ->
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            mainViewModel?.filterData?.observe(this, Observer {
                if (it != null) {
                    barListingViewModel.onFilterAdded(it)
                }
            })
        }
        barListingViewModel.triggerEventToView.observe(this, Observer {
            if (it == Constants.SHOW_SNACKBAR) {
                Snackbar.make(binding.parent, getString(R.string.alert_item_added), Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    fun onSearchTextChanged(newText: String?) {
        barListingViewModel.searchData(newText)
    }

}