package com.example.main.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.LifecycleOwner
import com.example.main.R
import com.example.main.databinding.FragmentGalleyBinding

import com.example.main.ui.main.adapter.GalleyAdapter
import com.example.main.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_galley.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.main.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class GalleyFragment : BaseFragment<FragmentGalleyBinding, MainViewModel>() {


    val list = ArrayList<Fragment>()
    val texts = ArrayList<String>()
    lateinit var adapter : GalleyAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
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