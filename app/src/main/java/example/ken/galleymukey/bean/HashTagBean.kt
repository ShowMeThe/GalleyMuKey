package example.ken.galleymukey.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import example.ken.galleymukey.BR

/**
 * example.ken.galleymukey.bean
 * cuvsu
 * 2019/5/21
 */
class HashTagBean(imgPath: String?, hashTag: String?) : Observable {

    @get:Bindable
    var imgPath: String? = imgPath
        set(imgPath) {
            field = imgPath
            notifyChange(BR.imgPath)
        }
    @get:Bindable
    var hashTag: String? = hashTag
        set(hashTag) {
            field = hashTag
            notifyChange(BR.hashTag)
        }
    @get:Bindable
    var isActive: Boolean = false
        set(active) {
            field = active
            notifyChange(BR.active)
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
