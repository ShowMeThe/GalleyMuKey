package com.example.home.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.database.bean.PhotoWallBean
import com.example.home.R
import com.example.home.databinding.FragmentPhotoBinding

import com.example.home.main.adapter.PhotoAdapter
import com.example.home.main.vm.HomeViewModel

import kotlinx.android.synthetic.main.fragment_photo.*
import showmethe.github.kframework.base.BaseFragment
import com.example.router.dialog.SeeCommentDialog
import showmethe.github.kframework.divider.RecycleViewDivider


/**
 * com.example.home.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class PhotoFragment : BaseFragment<FragmentPhotoBinding, HomeViewModel>() {



    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : PhotoAdapter
    val dialog  = SeeCommentDialog()
    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_photo

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {

        viewModel.bean.observe(this, Observer {
            it?.apply {
                smrl.showContent()
                refresh.isRefreshing = false
                list.clear()
                list.addAll(this)
            }
        })

        viewModel.commtList.observe(this, Observer {
            if(isVisible){
                it?.apply {
                    if(isEmpty()){
                        showToast("Found No comment")
                    }else{
                        dialog.list.clear()
                        dialog.list.addAll(this)
                        dialog.show(childFragmentManager,"dialog")
                    }
                }
            }
        })
    }



    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)


        adapter = PhotoAdapter(context,list)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        rv.addItemDecoration(RecycleViewDivider(context,RecyclerView.VERTICAL,1,ContextCompat.getColor(context,R.color.colorPrimaryDark)))

        viewModel.getHomePhoto()
    }

    override fun initListener() {

        refresh.setOnRefreshListener {
            viewModel.getHomePhoto()
        }



        adapter.setOnLikeClickListener { id, like ->
            viewModel.setLike(id, like)
        }

        adapter.setOnCommentClickListener {
            viewModel.getCommentById(list[it].id)
        }

    }
}