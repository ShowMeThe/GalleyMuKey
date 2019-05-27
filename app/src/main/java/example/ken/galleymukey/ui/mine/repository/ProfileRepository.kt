package example.ken.galleymukey.ui.mine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.UserInfoBean
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.UserInfoDto
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.match.MD5

/**
 * example.ken.galleymukey.ui.mine.repository
 * cuvsu
 * 2019/5/25
 **/
class ProfileRepository : BaseRepository() {

    val userInfoDao = DataSourceBuilder.getUserDao()


    fun updateInfo(bean : UserInfoBean,result : MutableLiveData<Int>){
        val dto  = UserInfoDto()
        dto.id = bean.id
        dto.account = bean.account
        dto.desContent = bean.desContent
        dto.avatar = bean.avatar
        dto.phone = bean.phone
        dto.password = bean.password
        dto.email = bean.email
        userInfoDao.updateUserInfo(dto).apply {
            result.value = this
        }
    }


    fun updatePassword(bean  : UserInfoBean,password: String,result: MutableLiveData<Int>){
        userInfoDao.queryUserInfo(bean.account).apply {
            if(password.equals(this.password)){
                showToast("New Password should not equals Old Password")
            }else{
                userInfoDao.updatePassword(bean.account,password).apply {
                    result.value = this
                }
            }
        }

    }

    fun queryAccount(account:String,bean:MutableLiveData<UserInfoBean>){
        userInfoDao.queryAccount(account).observe(owner!!, Observer {
            it?.apply {
                val userInfoBean = UserInfoBean()
                userInfoBean.account = account
                userInfoBean.avatar = avatar
                userInfoBean.desContent = desContent
                userInfoBean.email = email
                userInfoBean.id = id
                userInfoBean.password = password!!
                userInfoBean.phone = phone
                bean.value = userInfoBean
            }
        })
    }


}