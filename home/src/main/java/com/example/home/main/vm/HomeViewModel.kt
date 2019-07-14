package com.example.home.main.vm

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.database.bean.*
import com.example.database.source.dto.CommentDto
import com.example.database.source.dto.GoodsListDto
import com.example.home.main.repository.HomeRepository

import com.example.router.constant.PathConst
import showmethe.github.kframework.base.BaseViewModel

/**
 * com.example.home.main.vm
 * cuvsu
 * 2019/5/18
 **/

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val repository = HomeRepository()

    val bean  = MutableLiveData<ArrayList<PhotoWallBean>>()
    val hotBean = MutableLiveData<ArrayList<HotWallBean>>()
    val commtList = MutableLiveData<List<CommentDto>>()


    override fun onViewModelCreated(owner: LifecycleOwner) {


    }


    fun getCommentById(id:Int){
        repository.getCommentById(id,commtList)
    }

    fun addComment(id:Int,commemnt:String){
        repository.addComment(id,commemnt)
    }



    fun getHomePhoto(){
        repository.getHomePhoto(bean)
    }

    fun getHotWall(){
        repository.getHotWall(hotBean)
    }


    fun setLike(id :Int,like: Boolean){
        repository.setLike(id,like)
    }


    fun findAllLike(){
        repository.findLikeAll(bean)
    }

}