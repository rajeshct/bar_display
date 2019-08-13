package com.hackerearth.searchbar.base

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : Observable, AndroidViewModel(application) {
    private val propertyRegister = PropertyChangeRegistry()
    val triggerEventToView = MutableLiveData<Int>()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyRegister.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyRegister.add(callback)
    }

    protected fun notifyChange() {
        propertyRegister.notifyCallbacks(this, 0, null)
    }

    protected fun notifyPropertyChange(id: Int) {
        propertyRegister.notifyCallbacks(this, id, null)
    }

}