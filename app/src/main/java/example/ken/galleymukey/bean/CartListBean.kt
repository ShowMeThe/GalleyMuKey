package example.ken.galleymukey.bean


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import example.ken.galleymukey.BR

class CartListBean : Observable {

    internal var cardId = 0
    internal var goodsId = 0
    internal var count = 0
    internal var goodsName = ""
    internal var coverImg = ""
    internal var goodsDes = ""
    @Transient
    private var propertyChangeRegistry: PropertyChangeRegistry? = PropertyChangeRegistry()


    @Bindable
    fun getCardId(): Int {
        return cardId
    }

    fun setCardId(cardId: Int) {
        this.cardId = cardId
        notifyChange(example.ken.galleymukey.BR.cardId)
    }

    @Bindable
    fun getGoodsId(): Int {
        return goodsId
    }

    fun setGoodsId(goodsId: Int) {
        this.goodsId = goodsId
        notifyChange(example.ken.galleymukey.BR.goodsId)
    }

    @Bindable
    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
        notifyChange(example.ken.galleymukey.BR.count)
    }

    @Bindable
    fun getGoodsName(): String {
        return goodsName
    }

    fun setGoodsName(goodsName: String) {
        this.goodsName = goodsName
        notifyChange(example.ken.galleymukey.BR.goodsName)
    }

    @Bindable
    fun getCoverImg(): String {
        return coverImg
    }

    fun setCoverImg(coverImg: String) {
        this.coverImg = coverImg
        notifyChange(example.ken.galleymukey.BR.coverImg)
    }

    @Bindable
    fun getGoodsDes(): String {
        return goodsDes
    }

    fun setGoodsDes(goodsDes: String) {
        this.goodsDes = goodsDes
        notifyChange(example.ken.galleymukey.BR.goodsDes)
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