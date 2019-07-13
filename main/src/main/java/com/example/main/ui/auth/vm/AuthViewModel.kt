package com.example.main.ui.auth.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.database.bean.LoginBean
import com.example.database.bean.RegisterBean
import com.example.database.source.dto.UserInfoDto
import com.example.main.ui.auth.repository.AuthRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * com.example.main.ui.auth.vm
 * cuvsu
 * 2019/5/16
 **/
class AuthViewModel(application: Application) : BaseViewModel(application){



    val repository = AuthRepository()
    val result = MutableLiveData<Boolean>()
    val auth = MutableLiveData<UserInfoDto>()
    val bannerList = MutableLiveData<ArrayList<String>>()



    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.init(owner)

    }


    fun register(bean : RegisterBean){
        repository.register(bean,result)
    }


    fun login(bean : LoginBean){
        repository.login(bean,auth)
    }

    fun getBanner(key : String){
        repository.getBanner(key,bannerList)
    }
}