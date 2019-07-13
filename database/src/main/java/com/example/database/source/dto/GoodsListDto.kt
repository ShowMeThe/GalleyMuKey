package com.example.database.source.dto

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*
import com.example.database.source.converters.ArraysConverters
import showmethe.github.kframework.BR
import java.io.Serializable

/**
 * com.example.database.source.dto
 * cuvsu
 * 2019/6/1
 **/

@Entity(tableName = "GoodsListDto")
@TypeConverters(ArraysConverters::class)
class GoodsListDto : Observable,Serializable{
    @PrimaryKey
    var id :Int = 0
    @get:Bindable
    var goodsName : String = ""
    set(goodsName){
        field = goodsName
        notifyChange(BR.goodsName)
    }

    @get:Bindable
    var coverImg : String = ""
        set(coverImg){
            field = coverImg
            notifyChange(BR.coverImg)
        }

    var imageList : ArrayList<String>? = null

    @get:Bindable
    var goodsDes : String = ""
        set(goodsDes){
            field = goodsDes
            notifyChange(BR.goodsDes)
        }
    var firstType : Int = 0

    @get:Bindable
    var price : String = ""
        set(price){
            field = price
            notifyChange(BR.price)
        }


    @get:Bindable
    var keyword :String = ""
    set(keyword) {
        field = keyword
        notifyChange(BR.keyword)
    }

    @Ignore
    var vibrantColor:Int = -1

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