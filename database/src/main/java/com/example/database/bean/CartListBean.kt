package com.example.database.bean


import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.Ignore
import showmethe.github.kframework.BR

class CartListBean : Observable {

    private var cardId = 0
    internal var goodsId = 0
    internal var count = 0
    internal var goodsName = ""
    internal var coverImg = ""
    internal var goodsDes = ""
    internal var price = ""
    @Ignore
    internal var check = false

    @Ignore
    var vibrantColor = -1
    @Ignore
    var drawable : Drawable? = null

    @Transient
    private var propertyChangeRegistry: PropertyChangeRegistry? = PropertyChangeRegistry()



    @Bindable
    fun isCheck(): Boolean {
        return check
    }

    fun setCheck(check: Boolean) {
        this.check = check
        notifyChange(BR.price)
    }

    @Bindable
    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
        notifyChange(BR.price)
    }

    @Bindable
    fun getCardId(): Int {
        return cardId
    }

    fun setCardId(cardId: Int) {
        this.cardId = cardId
        notifyChange(BR.cardId)
    }

    @Bindable
    fun getGoodsId(): Int {
        return goodsId
    }

    fun setGoodsId(goodsId: Int) {
        this.goodsId = goodsId
        notifyChange(BR.goodsId)
    }

    @Bindable
    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
        notifyChange(BR.count)
    }

    @Bindable
    fun getGoodsName(): String {
        return goodsName
    }

    fun setGoodsName(goodsName: String) {
        this.goodsName = goodsName
        notifyChange(BR.goodsName)
    }

    @Bindable
    fun getCoverImg(): String {
        return coverImg
    }

    fun setCoverImg(coverImg: String) {
        this.coverImg = coverImg
        notifyChange(BR.coverImg)
    }

    @Bindable
    fun getGoodsDes(): String {
        return goodsDes
    }

    fun setGoodsDes(goodsDes: String) {
        this.goodsDes = goodsDes
        notifyChange(BR.goodsDes)
    }

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
