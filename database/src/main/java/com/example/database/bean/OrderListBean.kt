package com.example.database.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import showmethe.github.kframework.BR

class OrderListBean : Observable {

    @get:Bindable
    var orderId = 0
        set(orderId) {
            field = orderId
            notifyChange(BR.orderId)
        }
    @get:Bindable
    var orderGoodsId = 0
        set(orderGoodsId) {
            field = orderGoodsId
            notifyChange(BR.orderGoodsId)
        }
    @get:Bindable
    var coverImg = ""
        set(coverImg) {
            field = coverImg
            notifyChange(BR.coverImg)
        }
    @get:Bindable
    var totalPrice = 0.0
        set(totalPrice) {
            field = totalPrice
            notifyChange(BR.totalPrice)
        }
    @get:Bindable
    var totalCount = 0
        set(totalCount) {
            field = totalCount
            notifyChange(BR.totalCount)
        }
    @get:Bindable
    var goodsName = ""
        set(goodsName) {
            field = goodsName
            notifyChange(BR.goodsName)
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
