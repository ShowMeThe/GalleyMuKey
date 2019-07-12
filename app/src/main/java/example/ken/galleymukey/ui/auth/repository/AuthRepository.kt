package example.ken.galleymukey.ui.auth.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.bean.RegisterBean
import example.ken.galleymukey.pojo.Userdata
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.Source
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.UserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import example.ken.galleymukey.api.auth
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.http.RetroHttp
import showmethe.github.kframework.http.coroutines.SuspendResult
import showmethe.github.kframework.util.match.MD5

/**
 * example.ken.galleymukey.ui.auth.repository
 *
 * 2019/5/17
 **/
class AuthRepository : BaseRepository() {

    val userInfoDao = DataSourceBuilder.getUserDao()
    val imageUrlDao = DataSourceBuilder.getImageDao()
    val api  = RetroHttp.createApi(auth::class.java)

    fun register(it: RegisterBean,result: MutableLiveData<Boolean>){

        GlobalScope.launch (Dispatchers.Main){
            userInfoDao.query(it.account,it.password).apply {
                if(value == null){
                    val info = UserInfoDto()
                    info.account = it.account
                    info.password = MD5.string2MD5(it.password!!)
                    info.avatar = Source.get().getBanner()[4]
                    info.customBg = Source.get().getBanner()[4]
                    userInfoDao.register(info)
                    result.value = true
                    snyRegister(it.account!!,info.password!!)
                }else{
                    showToast("Account has been signed up")
                    result.value = false
                }
            }
        }
    }


    private fun snyRegister(account:String,password:String) {
        SuspendResult<Userdata>(owner).success { response, message ->
            val result =  userInfoDao.updateUserId(response!!.userId,account)
            if(result == 1){
                showToast("Sync Successfully")
            }else{
                showToast("Sync Failed")
            }
        }.error { code, message ->
            showToast("Sync Failed")
        }.hold{
            val loginBean = LoginBean()
            loginBean.account = account
            loginBean.password = password
            api.register(loginBean)
        }
    }



    fun login(bean: LoginBean, result: MutableLiveData<UserInfoDto>){

        GlobalScope.launch (Dispatchers.Main){
            userInfoDao.queryUserInfo(bean.account).apply {

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