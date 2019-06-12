package example.ken.galleymukey.ui.auth.vm

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.bean.RegisterBean
import example.ken.galleymukey.source.dto.UserInfoDto
import example.ken.galleymukey.ui.auth.repository.AuthRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.auth.vm
 * cuvsu
 * 2019/5/16
 **/
class AuthViewModel(application: Application) : BaseViewModel(application){



    val repository = AuthRepository()
    val result = MutableLiveData<Boolean>()
    val auth = MutableLiveData<UserInfoDto>()
    val bannerList = MutableLiveData<ArrayList<String>>()


    override fun addObserver(lifecycle: Lifecycle) {
        lifecycle.addObserver(repository)
    }

    override fun onViewModelCreated(owner: LifecycleOwner) {

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