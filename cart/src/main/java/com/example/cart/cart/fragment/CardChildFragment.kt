package com.example.cart.cart.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cart.R

import com.example.database.source.dto.GoodsListDto
import com.example.cart.cart.adapter.CartHomeAdapter
import com.example.cart.cart.vm.GoodsViewModel
import com.example.cart.databinding.FragmentCartChildBinding

import kotlinx.android.synthetic.main.fragment_cart_child.*
import showmethe.github.kframework.base.LazyFragment

/**
 * com.example.cart.cart.fragment
 * cuvsu
 * 2019/6/4
 **/
class CardChildFragment : LazyFragment<FragmentCartChildBinding, GoodsViewModel>() {



    lateinit var homeAdapter : CartHomeAdapter
    val goodsBean = MutableLiveData<List<GoodsListDto>>()
    var data = ObservableArrayList<GoodsListDto>()
    var type = 0

    companion object{

        fun create(type:Int) : CardChildFragment{
            val bundle = Bundle()
            bundle.putInt("type",type)
            val fragment =  CardChildFragment()
            fragment.arguments = bundle
            return  fragment
        }

    }


    override fun initViewModel(): GoodsViewModel = createViewModel(GoodsViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_cart_child

    override fun onBundle(bundle: Bundle) {
        type = bundle.getInt("type",0)
    }

    override fun observerUI() {

        goodsBean.observe(this, Observer {
            it?.apply {
                smrl.showContent()
                refresh.isRefreshing = false
                data.clear()
                data.addAll(this)

            }
        })

    }

    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.getGoodsList(type,goodsBean)


    }


    override fun init() {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)
        refresh.isRefreshing = true
        homeAdapter = CartHomeAdapter(context,data)
        rvBottom.adapter = homeAdapter
        rvBottom.layoutManager = GridLayoutManager(context,2)



    }

    override fun initListener() {

        refresh.setOnRefreshListener {
            viewModel.getGoodsList(type,goodsBean)
        }


    }
}