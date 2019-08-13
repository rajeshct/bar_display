package com.hackerearth.searchbar.utils

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hackerearth.searchbar.R
import com.hackerearth.searchbar.base.BaseFragment

object FragmentOperation {

    fun showDialog(context: Context, openFragment: DialogFragment, tag: String) {
        try {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val ft = fragmentManager.beginTransaction()
            val prev = fragmentManager.findFragmentByTag(tag)
            if (prev != null) {
                ft.remove(prev)
            }
            openFragment.show(ft, tag)
        } catch (exp: Exception) {
            //
        }
    }

    /**
     * Open fragment.
     *
     * @param frameContent frame content
     * @param manager      manager
     * @param newFragment  clazz
     * @param bundle       bundle
     */
    fun openFragment(
        frameContent: Int, manager: FragmentManager, newFragment: Fragment, tag: String
        , bundle: Bundle?, addToBackstack: Boolean, backstackTag: String
    ) {
        val transaction = manager.beginTransaction()
        if (!isFragmentAdded(manager, tag)) {
            try {
                val currentFragment = getCurrentFragment(manager)
                if (currentFragment != null) {
                    transaction.hide(currentFragment)
                }
                addNewFragment(frameContent, newFragment, tag, bundle, manager, addToBackstack, backstackTag)
            } catch (e: Exception) {
                //
            }

        } else {
            showFragment(manager, tag, transaction)
        }
    }

    private fun addNewFragment(
        containerId: Int, fragment: Fragment, fragmentTag: String, args: Bundle?
        , fm: FragmentManager, addToBackstack: Boolean, backstackTag: String
    ) {
        val ft = fm.beginTransaction()
        try {
            if (args != null) {
                fragment.arguments = args
            }


            ft.setCustomAnimations(
                R.anim.from_right_in,
                R.anim.slide_to_right, R.anim.from_right_in, R.anim.slide_to_right
            )

            ft.add(containerId, fragment, fragmentTag)

            if (addToBackstack) {
                ft.addToBackStack(backstackTag).commit()
            } else {
                ft.commit()
            }

        } catch (e: Exception) {
            try {
                ft.commitAllowingStateLoss()
            } catch (e1: Exception) {
            }

        }

    }

    private fun isFragmentAdded(manager: FragmentManager, tag: String): Boolean {
        val fragmentList = manager.fragments
        if (!GenricUtils.isListEmpty(fragmentList)) {
            for (fragment in fragmentList) {
                if (fragment is BaseFragment && StringUtils.equals(fragment.tagValue(), tag))
                    return true
            }
        }
        return false
    }

    private fun showFragment(manager: FragmentManager, tag: String, transaction: FragmentTransaction) {
        val fragmentList = manager.fragments
        if (!GenricUtils.isListEmpty(fragmentList))
            for (fragment in fragmentList) {
                if (fragment is BaseFragment) {
                    if (StringUtils.equalsIgnoreCase(fragment.tagValue(), tag)) {
                        transaction.show(fragment)
                    } else {
                        transaction.hide(fragment)
                    }
                }
            }
        transaction.commit()
    }

    private fun getCurrentFragment(manager: FragmentManager): Fragment? {
        val fragmentList = manager.fragments
        if (!GenricUtils.isListEmpty(fragmentList))
            for (fragment in fragmentList) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        return null
    }
}