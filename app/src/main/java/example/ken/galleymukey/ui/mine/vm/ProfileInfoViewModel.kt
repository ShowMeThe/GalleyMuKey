package example.ken.galleymukey.ui.mine.vm

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.bean.UserInfoBean
import example.ken.galleymukey.ui.cart.repository.CartRepository
import example.ken.galleymukey.ui.mine.repository.ProfileRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.mine.vm
 *
 * 2019/5/25
 **/
class ProfileInfoViewModel(application: Application) : BaseViewModel(application) {


    private val repository = ProfileRepository()
    private val cartRepository = CartRepository()

    val switchToReset = MutableLiveData<Boolean>()
    var currentType = true //true info //false reset
    var updateInfo = MutableLiveData<Int>()
    var updatePswd = MutableLiveData<Int>()
    var bean = MutableLiveData<UserInfoBean>()
    var updateController = MutableLiveData<Boolean>()
    var data = MutableLiveData<List<OrderListBean>>()


    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.init(owner)
        cartRepository.init(owner)

    }


    fun updateInfo(){
        repository.updateInfo(bean.value!!,updateInfo)
    }


    fun updatePassword(password:String){
        repository.updatePassword(bean.value!!,password,updatePswd)
    }

    fun queryAccount(account:String){
        repository.queryAccount(account,bean)
    }

    fun qureyOrderList(pagerNumber:Int){
        cartRepository.qureyOrderList(pagerNumber,data)
    }

}