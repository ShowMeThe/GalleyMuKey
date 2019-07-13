package com.example.main.ui.main.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.example.database.bean.HotWallBean
import com.example.main.R
import com.example.main.databinding.FragmentHotBinding

import com.example.main.ui.main.ImageShowActivity
import com.example.main.ui.main.adapter.HotAdapter
import com.example.main.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_hot.*
import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.main.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class HotFragment : BaseFragment<FragmentHotBinding, MainViewModel>() {


    val list = ObservableArrayList<HotWallBean>()
     lateinit var adapter : HotAdapter
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
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

    override fun init(savedInstanceState: Bundle?) {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)

        adapter = HotAdapter(context,list)
        rv.layoutManager = GridLayoutManager(context,3)
        rv.adapter = adapter



    }


    override fun onLifeCreated(owner: LifecycleOwner) {

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