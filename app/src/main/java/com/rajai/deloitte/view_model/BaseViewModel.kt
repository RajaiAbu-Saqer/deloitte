package com.rajai.deloitte.view_model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {
    var topsLiveData: MutableLiveData<T>? = null
    internal fun getLiveData(): MutableLiveData<T>? {
        if (topsLiveData == null)
            topsLiveData = MutableLiveData()
        return topsLiveData;
    }

    fun addObserver(
        owner: LifecycleOwner,
        observer: androidx.lifecycle.Observer<T>?
    ): BaseViewModel<T> {
        observer?.let { getLiveData()?.observe(owner, it) }
        return this
    }
}
