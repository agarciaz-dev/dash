package com.eelseth.presentation.utils

abstract class DiffDispatcher<T> {

    private var data: T? = null

    abstract fun onDataUpdated(oldData: T?, newData: T)

    fun setData(data: T) {
        onDataUpdated(this.data, data)
        this.data = data
    }

    fun clearData() {
        this.data = null
    }
}
