package com.example.cate.cate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.LifecycleOwner
import com.example.cate.R
import com.example.cate.cate.adapter.GalleyAdapter
import com.example.cate.cate.vm.CateViewModel
import com.example.cate.databinding.FragmentSearchBinding

import kotlinx.android.synthetic.main.fragment_search.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.cate.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class SearchFragment : BaseFragment<FragmentSearchBinding, CateViewModel>() {


    lateinit var adapter: GalleyAdapter

    override fun initViewModel(): CateViewModel = createViewModel(CateViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_search

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {

        initVp()


    }


    fun initVp(){
        val titles = ArrayList<String>()
        titles.add("User")

        val fragments = ArrayList<Fragment>()
        fragments.add(SearchUserFragment())

        adapter = GalleyAdapter(titles,fragments,childFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        tab.setupWithViewPager(viewPager,false)
    }


    override fun onLifeCreated(owner: LifecycleOwner) {


    }
    override fun initListener() {


    }
}