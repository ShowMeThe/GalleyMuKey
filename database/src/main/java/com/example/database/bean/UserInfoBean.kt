package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import showmethe.github.kframework.BR
import showmethe.github.kframework.util.StringUtil

/**
 * com.example.database.bean
 * cuvsu
 * 2019/5/25
 */
class UserInfoBean : Observable {

    var id = 0

    @get:Bindable
    var userId = ""
        set(userId) {
            field = userId
            notifyChange(BR.userId)
        }
    @get:Bindable
    var account = ""
        set(account) {
            field = account
            notifyChange(BR.account)
        }
    @get:Bindable
    var password = ""
        set(password) {
            field = password
            notifyChange(BR.password)
        }
    @get:Bindable
    var phone = ""
        set(phone) {
            field = phone
            notifyChange(BR.phone)
        }
    @get:Bindable
    var desContent = ""
        set(desContent) {
            field = desContent
            notifyChange(BR.desContent)
        }
    @get:Bindable
    var avatar = ""
        set(avatar) {
            field = avatar
            notifyChange(BR.avatar)
        }
    @get:Bindable
    var email = ""
        set(email) {
            field = email
            notifyChange(BR.email)
        }

    @get:Bindable
    var birthday = ""
        set(birthday) {
            field = birthday
            notifyChange(BR.birthday)
        }

    @get:Bindable
    var address = ""
        set(address) {
            field = address
            notifyChange(BR.address)
        }

    @get:Bindable
    var customBg = ""
        set(customBg) {
            field = customBg
            notifyChange(BR.customBg)
        }

    @get:Bindable
    var wallet = 0.0
        set(wallet) {
            field = StringUtil.double2Decimal(wallet).toDouble()
            notifyChange(BR.wallet)
        }

    @get:Bindable
    var totalSpend = 0.0
        set(totalSpend) {
            field = StringUtil.double2Decimal(totalSpend).toDouble()
            notifyChange(BR.totalSpend)
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
