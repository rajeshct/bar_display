package com.hackerearth.searchbar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class BarListingEntity(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Int,

    @SerializedName("abv")
    @Expose
    var abv: String,

    @SerializedName("ibu")
    @Expose
    var ibu: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("style")
    @Expose
    var style: String,

    @SerializedName("ounces")
    @Expose
    var ounces: Double
)