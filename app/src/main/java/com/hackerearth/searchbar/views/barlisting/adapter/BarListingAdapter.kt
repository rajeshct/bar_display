package com.hackerearth.searchbar.views.barlisting.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hackerearth.searchbar.BR
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.base.BaseAdapter
import com.hackerearth.searchbar.database.entity.BarListingEntity
import com.hackerearth.searchbar.utils.BeverageDiffUtil
import com.hackerearth.searchbar.views.barlisting.viewmodel.BareListingViewModel


class BarListingAdapter(private val data: MutableList<BarListingEntity>, private val viewModel: BareListingViewModel?) :
    BaseAdapter() {

    override fun getObjectForPosition(position: Int): BarListingEntity? {
        return data[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_bar_listing
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.getViewHolder().setVariable(BR.listingViewModel, viewModel)
        super.onBindViewHolder(holder, position)
    }

    fun updateData(refreshedData: MutableList<BarListingEntity>) {
        if (refreshedData.isEmpty()) {
            this.data.clear()
            notifyDataSetChanged()
        } else {
            val diffCallback = BeverageDiffUtil(data, refreshedData)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.data.clear()
            this.data.addAll(refreshedData)
            diffResult.dispatchUpdatesTo(this)
        }
    }
}