package com.hackerearth.searchbar.views.cartlisting.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackerearth.searchbar.BR
import com.hackerearth.searchbar.base.BaseViewModel
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.repository.Repository
import com.hackerearth.searchbar.utils.GenricUtils
import com.hackerearth.searchbar.views.barlisting.adapter.BarListingAdapter

@BindingAdapter("cartListingAdapter")
fun setListingAdapter(recyclerView: RecyclerView, bareListingViewModel: CartListingViewModel) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = BarListingAdapter(bareListingViewModel.data, null)
}

class CartListingViewModel(application: Application) : BaseViewModel(application) {

    private val repository = Repository()

    @Bindable
    @get:Bindable
    var noRecords: Boolean = false
        @Bindable
        set(value) {
            field = value
            notifyPropertyChange(BR.noRecords)
        }

    @Bindable
    val filteredRecords: MutableList<BarListingEntity> = mutableListOf()

    val data: MutableList<BarListingEntity> = mutableListOf()

    fun init() {
        repository.getCompositeDisposable().add(repository.fetchCartDetails().subscribe({
            refreshListing(it)
        }, {}))
    }

    private fun refreshListing(it: List<BarListingEntity>) {
        filteredRecords.clear()
        filteredRecords.addAll(it)
        notifyPropertyChange(BR.filteredRecords)
        noRecords = GenricUtils.isListEmpty(it)
    }

}