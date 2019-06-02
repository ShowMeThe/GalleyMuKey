package example.ken.galleymukey.source.dto

import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import example.ken.galleymukey.BR
import example.ken.galleymukey.source.converters.ArraysConverters
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * example.ken.galleymukey.source.dto
 * cuvsu
 * 2019/6/1
 **/

@Entity(tableName = "GoodsListDto")
@TypeConverters(ArraysConverters::class)
class GoodsListDto : Observable,Serializable{
    @PrimaryKey(autoGenerate = true)
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
    var secondType : Int = 0

    @get:Bindable
    var price : String = ""
        set(price){
            field = price
            notifyChange(BR.price)
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