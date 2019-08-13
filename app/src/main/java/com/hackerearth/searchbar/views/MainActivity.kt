package com.hackerearth.searchbar.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hackerearth.searchbar.BR
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.databinding.ActivityMainBinding
import com.hackerearth.searchbar.utils.Constants
import com.hackerearth.searchbar.utils.FragmentOperation
import com.hackerearth.searchbar.views.barlisting.BarListingFragment
import com.hackerearth.searchbar.views.cartlisting.CartListingFragment

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var viewModel: MainViewModel
    lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainActivityBinding.setVariable(BR.viewModel, viewModel)
        FragmentOperation.openFragment(
            R.id.container, supportFragmentManager, BarListingFragment(),
            Constants.TAG_BARLISTINGFRAGMENT, null, true, Constants.TAG_BARLISTINGFRAGMENT
        )
        mainActivityBinding.searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (getCurrentFragment() is BarListingFragment) {
            (getCurrentFragment() as BarListingFragment).onSearchTextChanged(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (getCurrentFragment() is BarListingFragment) {
            (getCurrentFragment() as BarListingFragment).onSearchTextChanged(newText)
        }
        return true
    }

    fun openCart(view: View) {
        if (getCurrentFragment() is CartListingFragment) {
            return
        }
        setVisibility(View.GONE)
        FragmentOperation.openFragment(
            R.id.container, supportFragmentManager, CartListingFragment()
            , Constants.TAG_CARTLISTING, null, true, Constants.TAG_CARTLISTING
        )
    }

    private fun setVisibility(visibility: Int) {
        mainActivityBinding.searchView.visibility = visibility
        if (visibility == View.VISIBLE) {
            mainActivityBinding.filter.show()
        } else {
            mainActivityBinding.filter.hide()
        }
        mainActivityBinding.ivCart.visibility = visibility
    }


    override fun onBackPressed() {
        if (getCurrentFragment() is BarListingFragment) {
            super.finish()
        } else {
            setVisibility(View.VISIBLE)
            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.container)
    }
}
