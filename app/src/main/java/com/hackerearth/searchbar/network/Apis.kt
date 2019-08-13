package com.startup.news.application.network

import com.hackerearth.searchbar.database.entity.BarListingEntity
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by admin on 12/27/2017.
 */
interface Apis {

    @GET("beercraft")
    fun fetchBarList(): Single<List<BarListingEntity>>

}