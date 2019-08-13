package com.hackerearth.searchbar.utils

import androidx.recyclerview.widget.DiffUtil

import com.hackerearth.searchbar.database.entity.BarListingEntity


/**
 * The type Project diff util.
 */
/**
 * Instantiates a new Project diff util.
 *
 * @param oldDateUiModelList the old date ui model list
 * @param newDateUiModelList the new date ui model list
 */
class BeverageDiffUtil(
    private val mOldDateUiModelList: List<BarListingEntity>
    , private val mNewDateUiModelList: List<BarListingEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldDateUiModelList.size
    }

    override fun getNewListSize(): Int {
        return mNewDateUiModelList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDateUiModelList[oldItemPosition].id == mNewDateUiModelList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDateUiModelList[oldItemPosition].name
            .equals(mNewDateUiModelList[newItemPosition].name, true)
    }

}