package example.ken.galleymukey.ui.auth.repository

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.bean.RegisterBean
import example.ken.galleymukey.source.AppDataBaseCreator
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.UserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.match.MD5

/**
 * example.ken.galleymukey.ui.auth.repository
 *
 * 2019/5/17
 **/
class AuthRepository : BaseRepository() {

    val userInfoDao = DataSourceBuilder.getUserDao()
    val imageUrlDao = DataSourceBuilder.getImageDao()

    fun register(it: RegisterBean,result: MutableLiveData<Boolean>){
        showLoading()
        GlobalScope.launch (Dispatchers.Main){
            userInfoDao.query(it.account,it.password).apply {
                if(value == null){
                    val info = UserInfoDto()
                    info.account = it.account
                    info.password = MD5.string2MD5(it.password!!)
                    userInfoDao.register(info)
                    dismissLoading()
                    result.value = true
                }else{
                    showToast("Account has been signed up")
                    result.value = false
                }
            }
        }
    }


    fun login(bean: LoginBean, result: MutableLiveData<UserInfoDto>){
        showLoading()
        GlobalScope.launch (Dispatchers.Main){
            userInfoDao.queryUserInfo(bean.account).apply {
                dismissLoading()
                if(this!=null){
                    if(password.equals(MD5.string2MD5(bean.password!!))){
                        result.value  = this
                    }else{
                        showToast("Password error")
                    }
                }else{
                    showToast("Account not found")
                }
            }
        }
    }


    fun getBanner(key:String,result: MutableLiveData<ArrayList<String>>){
        GlobalScope.launch (Dispatchers.Main){
            imageUrlDao.getImages(key).observe(owner!!,
                Observer<ImageUrlDto> {
                    if(it!=null){
                        result.value = it.arrarys
                    }
                })
        }
    }

}