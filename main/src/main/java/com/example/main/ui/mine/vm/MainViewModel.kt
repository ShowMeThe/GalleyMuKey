package com.example.main.ui.mine.vm

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.database.bean.HashTagBean
import com.example.main.ui.mine.repository.MainRepertory

import com.example.router.constant.PathConst
import com.example.router.share.Share

import showmethe.github.kframework.base.BaseViewModel

/**
 * com.example.home.main.vm
 * cuvsu
 * 2019/5/18
 **/

class MainViewModel(application: Application) : BaseViewModel(application){

    val repository = MainRepertory()
    val customBg = MutableLiveData<String>()
    val hashTag = MutableLiveData<ArrayList<HashTagBean>>()


    override fun onViewModelCreated(owner: LifecycleOwner) {



    }




    fun getCustomBg(){
        repository.getCustomBg(customBg)
    }

    fun setCustomBg(newBg : String){
        repository.setCustomBg(newBg)
    }


}