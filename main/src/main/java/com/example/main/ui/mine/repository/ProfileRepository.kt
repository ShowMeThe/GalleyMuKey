package com.example.main.ui.mine.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.database.bean.OrderListBean
import com.example.main.api.mine
import com.example.database.bean.UserInfoBean
import com.example.database.source.DataSourceBuilder
import com.example.database.source.dto.UserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.http.RetroHttp
import showmethe.github.kframework.http.coroutines.SuspendResult

/**
 * com.example.main.ui.mine.repository
 * cuvsu
 * 2019/5/25
 **/
class ProfileRepository : BaseRepository() {

    val userInfoDao = DataSourceBuilder.getUserDao()
    val api = RetroHttp.createApi(mine::class.java)
    val orderDao = DataSourceBuilder.getOrderDao()

    fun updateInfo(bean : UserInfoBean,result : MutableLiveData<Int>){
        val dto  = UserInfoDto()
        dto.id = bean.id
        dto.userId = bean.userId
        dto.account = bean.account
        dto.desContent = bean.desContent
        dto.avatar = bean.avatar
        dto.phone = bean.phone
        dto.password = bean.password
        dto.email = bean.email
        dto.birthday = bean.birthday
        dto.address = bean.address
        dto.customBg = bean.customBg

        userInfoDao.updateUserInfo(dto).apply {
            result.value = this
            snycInfo(dto)
        }
    }


    private fun snycInfo(dto:UserInfoDto) {
        val map = HashMap<String,Any>()
        map["userId"] = dto.userId
        map["nickName"] = dto.account!!
        map["phone"] = dto.phone
        map["birthday"] = dto.birthday
        map["email"] = dto.email
        map["introduction"] = dto.desContent

        SuspendResult<String>(owner).success { response, message ->
            showToast("Snyc successfully")
        }.hold {
            api.updateInfo(dto)
        }
    }


    fun updatePassword(bean  : UserInfoBean,password: String,result: MutableLiveData<Int>){
        userInfoDao.queryUserInfo(bean.account).apply {
            if(password == this?.password){
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
                userInfoBean.id = id
                userInfoBean.account = account
                userInfoBean.avatar = avatar
                userInfoBean.desContent = desContent
                userInfoBean.email = email
                userInfoBean.userId = userId
                userInfoBean.password = password!!
                userInfoBean.phone = phone
                userInfoBean.birthday = birthday
                userInfoBean.wallet = wallet
                userInfoBean.totalSpend = totalSpend
                userInfoBean.address = address
                userInfoBean.customBg = customBg
                bean.value = userInfoBean
            }
        })
    }

    fun qureyOrderList(pagerNumber :Int ,data : MutableLiveData<List<OrderListBean>>){
        GlobalScope.launch(Dispatchers.IO){
            val result = orderDao.qureyOrderList((pagerNumber - 1)*10)
            data.postValue(result)
        }
    }

}