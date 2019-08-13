package com.hackerearth.searchbar.repository

import androidx.lifecycle.MutableLiveData
import com.hackerearth.searchbar.database.AppDatabase
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.database.entity.CartItemEntity
import com.startup.news.application.network.Apis
import com.startup.news.application.network.RetrofitClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository {

    private var appDatabase: AppDatabase = AppDatabase.instance
    private var retrofit: Apis = RetrofitClient.instance
    private var data = MutableLiveData<List<BarListingEntity>>()
    private val compositeDisposable = CompositeDisposable()

    fun getCompositeDisposable(): CompositeDisposable {
        return compositeDisposable
    }

    fun fetch(): MutableLiveData<List<BarListingEntity>> {
        compositeDisposable.add(appDatabase.barListingData().getAll()
            .flatMap {
                data.postValue(it)
                retrofit.fetchBarList()
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                data.postValue(it)
                appDatabase.barListingData().nukeTable()
                appDatabase.barListingData().insert(it)
            }, {

            })
        )
        return data
    }

    fun fetchCartDetails(): Single<List<BarListingEntity>> {
        return appDatabase.cartListData().getCartListing()
    }

    fun addItemInCart(id: Int) {
        appDatabase.cartListData().addItem(CartItemEntity(id))
    }

}