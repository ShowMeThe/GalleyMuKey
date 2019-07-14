package com.example.cate.cate.vm

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.cate.cate.repository.CateRepository
import com.example.cate.cate.repository.SearchRepository
import com.example.database.bean.CateTagBean
import com.example.database.bean.HashTagBean
import com.example.database.bean.NewGoodsBean
import com.example.database.bean.PhotoWallBean
import com.example.router.share.Share
import showmethe.github.kframework.base.BaseViewModel

class CateViewModel(application: Application) : BaseViewModel(application) {

    val repository = CateRepository()
    val searchRepository = SearchRepository()
    val cate = MutableLiveData<ArrayList<CateTagBean>>()
    val keyword = MutableLiveData<String>()
    val users = MutableLiveData<ArrayList<PhotoWallBean>>()
    val newGoods = MutableLiveData<List<NewGoodsBean>>()
    val hashTag = MutableLiveData<ArrayList<HashTagBean>>()
    val searchContent = MutableLiveData<String>()
    val popBack = MutableLiveData<Boolean>()
    val share = Share.get()

    override fun onViewModelCreated(owner: LifecycleOwner) {


        share.onPopBack {
            popBack.value = true
        }


    }


    fun updateFragmentManager(fragmentManager: FragmentManager){
        share.updateFragmentManager(fragmentManager)
    }

    fun getGoodsByHashTag(tag:String,pagerNumber : Int){
        repository.findGoodsByHashTag(tag,pagerNumber,newGoods)
    }

    fun searchUser(name:String){
        searchRepository.searchUser(name,users)
    }


    fun getCate(keyword: String){
        repository.getCate(keyword,cate)
    }


    fun getHashTag(){
        repository.getHashTag(hashTag)
    }

}