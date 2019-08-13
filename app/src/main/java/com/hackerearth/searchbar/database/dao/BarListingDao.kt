package com.hackerearth.searchbar.database.dao

import androidx.room.*
import com.hackerearth.searchbar.database.entity.BarListingEntity
import io.reactivex.Single

@Dao
interface BarListingDao {
    @Query("SELECT * FROM BarListingEntity")
    fun getAll(): Single<List<BarListingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<BarListingEntity>)

    @Delete
    fun delete(user: BarListingEntity)

    @Query("DELETE FROM BarListingEntity")
    fun nukeTable()
}