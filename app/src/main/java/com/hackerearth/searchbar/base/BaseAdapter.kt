package com.hackerearth.searchbar.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hackerearth.searchbar.BR


abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context)
                , viewType, parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val obj = getObjectForPosition(position)
        obj?.let { holder.bind(it, position) }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getObjectForPosition(position: Int): Any?

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    inner class BaseViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(any: Any, position: Int) {
            viewDataBinding.setVariable(BR.obj, any)
            viewDataBinding.setVariable(BR.position, position)
            viewDataBinding.executePendingBindings()
        }

        fun getViewHolder() = viewDataBinding
    }
}