package com.example.home.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route

import com.example.database.bean.HotWallBean
import com.example.home.R
import com.example.home.databinding.FragmentHotBinding


import com.example.home.main.ImageShowActivity
import com.example.home.main.adapter.HotAdapter
import com.example.home.main.vm.HomeViewModel
import com.example.router.constant.PathConst

import kotlinx.android.synthetic.main.fragment_hot.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.home.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class HotFragment : BaseFragment<FragmentHotBinding, HomeViewModel>() {


    val list = ObservableArrayList<HotWallBean>()
     lateinit var adapter : HotAdapter
    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_hot

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {
        viewModel.hotBean.observe(this, Observer {
            it?.apply {
                smrl.showContent()
                refresh.isRefreshing = false
                list.clear()
                list.addAll(this)
            }
        })

    }


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(this)
    }
    
    override fun init(savedInstanceState: Bundle?) {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)

        adapter = HotAdapter(context,list)
        rv.layoutManager = GridLayoutManager(context,3)
        rv.adapter = adapter


        viewModel.getHotWall()
    }



    override fun initListener() {

        refresh.setOnRefreshListener {
            viewModel.getHotWall()
        }

        adapter.setOnPhotoClickListener { view, url ->
            val bundle = Bundle()
            bundle.putString("photo",url)
            context.startActivity(bundle, ImageShowActivity::class.java,view,"photo")
        }
    }
}