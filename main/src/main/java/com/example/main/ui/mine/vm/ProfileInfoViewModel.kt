package com.example.main.ui.mine.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.database.bean.OrderListBean
import com.example.database.bean.UserInfoBean
import com.example.main.ui.mine.repository.ProfileRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * com.example.main.ui.mine.vm
 *
 * 2019/5/25
 **/
class ProfileInfoViewModel(application: Application) : BaseViewModel(application) {


    val repository = ProfileRepository()


    val switchToReset = MutableLiveData<Boolean>()
    var currentType = true //true info //false reset
    var updateInfo = MutableLiveData<Int>()
    var updatePswd = MutableLiveData<Int>()
    var bean = MutableLiveData<UserInfoBean>()
    var updateController = MutableLiveData<Boolean>()
    var data = MutableLiveData<List<OrderListBean>>()


    override fun onViewModelCreated(owner: LifecycleOwner) {

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

   /* fun qureyOrderList(pagerNumber:Int){
        cartRepository.qureyOrderList(pagerNumber,data)
    }*/

}