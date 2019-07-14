package com.example.home.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.LifecycleOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.home.R
import com.example.home.databinding.FragmentGalleyBinding


import com.example.home.main.adapter.GalleyAdapter
import com.example.home.main.vm.HomeViewModel
import com.example.router.constant.PathConst

import kotlinx.android.synthetic.main.fragment_galley.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.home.main.fragment
 * cuvsu
 * 2019/5/18
 **/
@Route(path = PathConst.HOME_FRAGMEMNT)
class GalleyFragment : BaseFragment<FragmentGalleyBinding, HomeViewModel>() {


    val list = ArrayList<Fragment>()
    val texts = ArrayList<String>()
    lateinit var adapter : GalleyAdapter

    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_galley

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {




    }

    override fun init(savedInstanceState: Bundle?) {
        initVp()


    }

    override fun initListener() {


    }
    override fun onLifeCreated(owner: LifecycleOwner) {

        
    }

    fun initVp(){
        list.add(PhotoFragment())
        list.add(HotFragment())
        texts.add("Photo")
        texts.add("Hot")

        adapter = GalleyAdapter(texts,list,childFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        tab.setupWithViewPager(viewPager,false)
    }


}