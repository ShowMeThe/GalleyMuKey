package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import showmethe.github.kframework.BR

/**
 * com.example.database.bean
 * cuvsu
 * 2019/5/25
 */
class RestPasswordBean : Observable {

    @get:Bindable
    var oldPassword = ""
        set(oldPassword) {
            field = oldPassword
            isEnable = !(oldPassword.isEmpty() || this.newPassword.isEmpty())
            notifyChange(BR.oldPassword)
        }

    @get:Bindable
    var newPassword = ""
        set(newPassword) {
            field = newPassword
            isEnable = !(this.oldPassword.isEmpty() || newPassword.isEmpty())
            notifyChange(BR.newPassword)
        }

    @get:Bindable
    var isEnable: Boolean = false
        set(enable) {
            field = enable
            notifyChange(BR.enable)
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
