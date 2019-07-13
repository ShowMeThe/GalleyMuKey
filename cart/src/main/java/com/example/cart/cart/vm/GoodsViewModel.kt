package com.example.cart.cart.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.database.bean.CartListBean
import com.example.database.source.dto.OrderDto
import com.example.cart.cart.repository.CartRepository
import com.example.database.source.dto.GoodsListDto
import showmethe.github.kframework.base.BaseViewModel

/**
 * com.example.cart.cart.vm
 * cuvsu
 * 2019/5/27
 **/
class GoodsViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CartRepository()

    val reuslt = MutableLiveData<List<CartListBean>>()
    val boolean = MutableLiveData<Boolean>()


    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.init(owner)

    }

    fun findCartList(){
        repository.findCartList(reuslt)
    }

    fun addCartBean(id:Int){
        repository.addCartBean(id,boolean)
    }

    fun addOrder(listBean: List<OrderDto>){
        repository.addOrder(listBean,boolean)
    }


    fun getGoodsList(first:Int,goodsBean : MutableLiveData<List<GoodsListDto>>){
        repository.getGoodsList(first,goodsBean)
    }

}