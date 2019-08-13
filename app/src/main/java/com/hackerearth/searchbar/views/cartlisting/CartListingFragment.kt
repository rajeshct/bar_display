package com.hackerearth.searchbar.views.cartlisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.base.BaseFragment
import com.hackerearth.searchbar.databinding.FragmentCartBinding
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.views.cartlisting.viewmodel.CartListingViewModel

class CartListingFragment : BaseFragment() {

    override fun tagValue(): String {
        return Constants.TAG_CARTLISTING
    }

    lateinit var viewModel: CartListingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentCartBinding>(inflater, R.layout.fragment_cart, container, false)
        viewModel = ViewModelProviders.of(this).get(CartListingViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

}