package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.google.gson.annotations.SerializedName
import showmethe.github.kframework.BR

/**
 * com.example.database.bean
 *
 *
 * 2019/5/17
 */
class LoginBean : Observable {

    @SerializedName("phone")
    @get:Bindable
    var account: String? = null
        set(account) {
            field = account
            notifyChange(BR.account)
        }

    @SerializedName("password")
    @get:Bindable
    var password: String? = null
        set(password) {
            field = password
            notifyChange(BR.password)
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
