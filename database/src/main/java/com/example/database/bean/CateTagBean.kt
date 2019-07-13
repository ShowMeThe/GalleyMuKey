package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import showmethe.github.kframework.BR

/**
 * com.example.database.bean
 * cuvsu
 * 2019/6/1
 */
class CateTagBean : Observable {

    @get:Bindable
    var id = 0
        set(id) {
            field = id
            notifyChange(BR.id)
        }
    @get:Bindable
    var keyword = ""
        set(keyword) {
            field = keyword
            notifyChange(BR.keyword)
        }
    @get:Bindable
    var img = ""
        set(img) {
            field = img
            notifyChange(BR.img)
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
