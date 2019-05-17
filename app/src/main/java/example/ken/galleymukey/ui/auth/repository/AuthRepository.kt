package example.ken.galleymukey.ui.auth.repository

import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.RegisterBean
import example.ken.galleymukey.source.AppDataBaseCreator
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.UserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository

/**
 * example.ken.galleymukey.ui.auth.repository
 *
 * 2019/5/17
 **/
class AuthRepository : BaseRepository() {

    val userInfoDao = DataSourceBuilder.getUserDao()


    fun register(it: RegisterBean,result: MutableLiveData<Boolean>){
        showLoading()
        GlobalScope.launch (Dispatchers.Main){
            userInfoDao.query(it.account,it.password).apply {
                if(value == null){
                    val info = UserInfoDto()
                    info.account = it.account
                    info.password = it.password
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



}