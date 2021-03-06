package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import showmethe.github.kframework.BR

/**
 * com.example.database.bean
 * cuvsu
 * 2019/5/18
 */
class HotWallBean(pathTop: String?, pathBottom: String?,type: Int) : Observable {


    @get:Bindable
    var pathBottom: String? = pathBottom
        set(pathBottom) {
            field = pathBottom
            notifyChange(BR.pathBottom)
        }

    @get:Bindable
    var pathTop: String? = pathTop
        set(pathTop) {
            field = pathTop
            notifyChange(BR.pathTop)
        }

    @get:Bindable
    var type: Int = type
        set(type) {
            field = type
            notifyChange(BR.type)
        }
    @Transient
    private var propertyChangeRegistry: PropertyChangeRegistry? = PropertyChangeRegistry()

    @Synchronized
    private fun notifyChange(propertyId: Int) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = PropertyChangeRegistry()
        }
        propertyChangeRegistry!!.notifyChange(this, propertyId)
    }

    @Synchronized
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = PropertyChangeRegistry()
        }
        propertyChangeRegistry!!.add(callback)

    }

    @Synchronized
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry!!.remove(callback)
        }
    }
}
