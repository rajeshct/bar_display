package com.hackerearth.searchbar.views.filters.viewmodel

import android.app.Application
import com.hackerearth.searchbar.base.BaseViewModel
import com.hackerearth.searchbar.data.FilterData
import com.hackerearth.searchbar.utils.Constants

class BarFilterViewModel(application: Application) : BaseViewModel(application) {

    var filterData = FilterData()

    fun onOrderClick(order: Int) {
        filterData.orderSelection = order
    }

    fun onSubmit() {
        if ((filterData.largeSelection
                    || filterData.aleSelection
                    || filterData.ipaSelection
                    || filterData.orderSelection != 0)
        ) {
            triggerEventToView.value = Constants.CLOSE_FILTER
        } else {
            triggerEventToView.value = Constants.SHOW_SNACKBAR
        }
    }
}