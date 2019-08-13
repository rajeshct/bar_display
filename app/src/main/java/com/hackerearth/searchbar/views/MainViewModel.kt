package com.hackerearth.searchbar.views

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hackerearth.searchbar.base.BaseViewModel
import com.hackerearth.searchbar.data.FilterData
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.utils.FragmentOperation
import com.hackerearth.searchbar.views.filters.BarFilterDialog

class MainViewModel(application: Application) : BaseViewModel(application) {

    // User for filter data in FilterListingFragment
    val filterData = MutableLiveData<FilterData>()

    fun onFilterClick(view: View) {
        FragmentOperation.showDialog(view.context, BarFilterDialog(), Constants.TAG_FILTER)
    }

}