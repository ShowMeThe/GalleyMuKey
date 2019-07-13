package com.example.main.ui.cate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.LifecycleOwner
import com.example.main.R
import com.example.main.databinding.FragmentSearchBinding

import com.example.main.ui.main.adapter.GalleyAdapter
import com.example.main.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_cate.*
import kotlinx.android.synthetic.main.fragment_search.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.main.ui.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {


    lateinit var adapter: GalleyAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

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