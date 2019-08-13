package com.hackerearth.searchbar.views.barlisting.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackerearth.searchbar.BR
import com.hackerearth.searchbar.base.BaseViewModel
import com.hackerearth.searchbar.data.FilterData
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.repository.Repository
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.utils.GenricUtils
import com.hackerearth.searchbar.views.barlisting.adapter.BarListingAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@BindingAdapter("barListingAdapter")
fun setListingAdapter(recyclerView: RecyclerView, bareListingViewModel: BareListingViewModel) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = BarListingAdapter(bareListingViewModel.data, bareListingViewModel)
}

@BindingAdapter("refreshRecyclerView")
fun refreshList(recyclerView: RecyclerView, refreshedData: MutableList<BarListingEntity>) {
    if (recyclerView.adapter is BarListingAdapter && !GenricUtils.isListEmpty(refreshedData)) {
        (recyclerView.adapter as BarListingAdapter).updateData(refreshedData)
        recyclerView.scrollToPosition(0)
    }
}

class BareListingViewModel(context: Application) : BaseViewModel(context) {

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

    private val allRecords: MutableList<BarListingEntity> = mutableListOf()

    val data: MutableList<BarListingEntity> = mutableListOf()

    private val barListingRepository = Repository()

    fun init() {
        barListingRepository.fetch().observeForever {
            allRecords.clear()
            allRecords.addAll(it)
            refreshListing(it)
        }
    }

    private fun refreshListing(it: List<BarListingEntity>) {
        filteredRecords.clear()
        filteredRecords.addAll(it)
        notifyPropertyChange(BR.filteredRecords)
        noRecords = GenricUtils.isListEmpty(it)
    }

    fun searchData(newText: String?) {
        barListingRepository.getCompositeDisposable().clear()
        val filterRecord = mutableListOf<BarListingEntity>()
        filterRecord.addAll(allRecords)

        barListingRepository.getCompositeDisposable().add(io.reactivex.Observable.fromIterable(filterRecord)
            .filter {
                var isContainData = false
                if (newText != null) {
                    isContainData = it.name.contains(newText, ignoreCase = true)
                }
                return@filter isContainData
            }.toList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshListing(it)
            }, {

            })
        )
    }

    fun onFilterAdded(filterData: FilterData) {
        barListingRepository.getCompositeDisposable().clear()
        val filterRecord = mutableListOf<BarListingEntity>()
        filterRecord.addAll(allRecords)
        val observableData = Observable.fromIterable(filterRecord).sorted { o1, o2 ->
            if (filterData.orderSelection == 1) {
                o1.name.length - o2.name.length
            } else {
                o2.name.length - o1.name.length
            }
        }
        if (filterData.aleSelection || filterData.ipaSelection || filterData.largeSelection) {
            barListingRepository.getCompositeDisposable().add(observableData.filter { test ->
                var isContainData = false
                when {
                    filterData.aleSelection -> isContainData = test.style.contains("ale", ignoreCase = true)
                    filterData.ipaSelection -> isContainData = test.style.contains("ipa", ignoreCase = true)
                    filterData.largeSelection -> isContainData = test.style.contains("lager", ignoreCase = true)
                }
                return@filter isContainData
            }.toList().subscribe { newRecords, _ ->
                run {
                    refreshListing(newRecords)
                }
            })
        } else {
            barListingRepository.getCompositeDisposable().add(observableData.toList().subscribe({
                refreshListing(it)
            }, {}))
        }

    }

    override fun onCleared() {
        super.onCleared()
        barListingRepository.getCompositeDisposable().dispose()
    }

    fun onCartClick(id: Int) {
        barListingRepository.addItemInCart(id)
        triggerEventToView.value = Constants.SHOW_SNACKBAR
    }

}