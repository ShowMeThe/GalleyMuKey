package example.ken.galleymukey.ui.cart.vm

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.source.dto.CartListDto
import example.ken.galleymukey.ui.cart.repository.CartRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.cart.vm
 * cuvsu
 * 2019/5/27
 **/
class GoodsViewModel(application: Application) : BaseViewModel(application) {

    val repository = CartRepository()
    val reuslt = MutableLiveData<List<CartListBean>>()

    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.init(owner)

    }

    fun findCartList(pager:Int ){
        repository.findCartList(pager,reuslt)
    }

    fun addCartBean(bean:CartListDto){
        repository.addCartBean(bean)
    }


}