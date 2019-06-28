package example.ken.galleymukey.ui.cart.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.source.dto.OrderDto
import example.ken.galleymukey.ui.cart.repository.CartRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.cart.vm
 * cuvsu
 * 2019/5/27
 **/
class GoodsViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CartRepository()

    val reuslt = MutableLiveData<List<CartListBean>>()
    val boolean = MutableLiveData<Boolean>()


    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.init(owner)

    }

    fun findCartList(){
        repository.findCartList(reuslt)
    }

    fun addCartBean(id:Int){
        repository.addCartBean(id,boolean)
    }

    fun addOrder(listBean: List<OrderDto>){
        repository.addOrder(listBean,boolean)
    }

    fun qureyOrderList(){
        repository.qureyOrderList()
    }


}