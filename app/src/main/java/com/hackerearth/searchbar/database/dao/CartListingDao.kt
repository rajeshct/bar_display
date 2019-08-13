package com.hackerearth.searchbar.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.database.entity.CartItemEntity
import io.reactivex.Single

@Dao
interface CartListingDao {

    @Query(
        """SELECT * FROM BarListingEntity,CartItemEntity
           WHERE BarListingEntity.id== CartItemEntity.id"""
    )
    fun getCartListing(): Single<List<BarListingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: CartItemEntity)
}