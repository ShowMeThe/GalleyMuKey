package com.example.main.ui.mine.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.database.source.DataSourceBuilder
import com.example.router.constant.RdenConstant
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.rden.RDEN

class MainRepertory : BaseRepository() {


    val infoDao = DataSourceBuilder.getUserDao()


    fun getCustomBg(result : MutableLiveData<String>){
        infoDao.getCustom(RDEN.get(RdenConstant.account,"")).observe(owner!!, Observer {
            it?.apply {
                result.value = it
            }
        })
    }


    fun setCustomBg(newBg : String){
        infoDao.updateCustom(RDEN.get(RdenConstant.account,""),newBg)
    }
}