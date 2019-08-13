package com.hackerearth.searchbar.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hackerearth.CustomApplication
import com.hackerearth.searchbar.database.dao.BarListingDao
import com.hackerearth.searchbar.database.dao.CartListingDao
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.database.entity.CartItemEntity

@Database(entities = [BarListingEntity::class, CartItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun barListingData(): BarListingDao
    abstract fun cartListData(): CartListingDao

    private object Holder {
        val INSTANCE = synchronized(AppDatabase::class.java) {
            Room.databaseBuilder(
                CustomApplication.instance.applicationContext,
                AppDatabase::class.java, "newsApp"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }

    companion object {
        val instance: AppDatabase by lazy { Holder.INSTANCE }
    }

}