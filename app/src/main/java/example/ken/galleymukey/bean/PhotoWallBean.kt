package example.ken.galleymukey.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import example.ken.galleymukey.BR
import java.util.ArrayList

/**
 * example.ken.galleymukey.bean
 * cuvsu
 * 2019/5/18
 */
class PhotoWallBean : Observable {

    var imagePaths: ArrayList<String>? = null

    var currentPos: Int = 0

    @get:Bindable
    var avatar : String? = null
        set(avatar) {
            field = avatar
            notifyChange(BR.avatar)
        }

    @get:Bindable
    var username : String? = null
        set(username) {
            field = username
            notifyChange(BR.username)
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
