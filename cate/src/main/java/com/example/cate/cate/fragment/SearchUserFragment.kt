package com.example.cate.cate.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cate.R

import com.example.database.bean.PhotoWallBean

import com.example.cate.cate.adapter.UserListAdapter
import com.example.cate.cate.vm.CateViewModel

import kotlinx.android.synthetic.main.fragment_search_user.*
import showmethe.github.kframework.base.LazyFragment

/**
 * com.example.cate.cate.fragment
 * cuvsu
 * 2019/6/8
 **/
class SearchUserFragment : LazyFragment<ViewDataBinding, CateViewModel>() {

    val datas = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter: UserListAdapter

    override fun initViewModel(): CateViewModel = createViewModel(CateViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_search_user

    override fun onBundle(bundle: Bundle) {


    }

    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.searchRepository.init(this)
    }


    override fun observerUI() {

        viewModel.searchContent.observe(this, Observer {
            it?.apply {
                if(isNotEmpty()){
                    viewModel.searchUser(this)
                }else{
                    datas.clear()
                }

            }
        })


        viewModel.users.observe(this, Observer {
            it?.apply {
                datas.clear()
                datas.addAll(this)
            }
        })

    }

    override fun init() {
        adapter = UserListAdapter(context,datas)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

    }

    override fun initListener() {


    }
}