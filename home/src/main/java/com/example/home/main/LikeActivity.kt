package com.example.home.main

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route

import com.example.database.bean.PhotoWallBean
import com.example.home.R
import com.example.home.databinding.ActivityLikeBinding

import com.example.router.dialog.AddCommentDialog
import com.example.home.main.adapter.LikeListAdapter
import com.example.home.main.vm.HomeViewModel
import com.example.router.constant.PathConst
import kotlinx.android.synthetic.main.activity_like.*
import kotlinx.android.synthetic.main.include_title.*
import showmethe.github.kframework.base.BaseActivity

@Route(path = PathConst.HOME_LIKE_ACTIVITY)
class LikeActivity : BaseActivity<ActivityLikeBinding,HomeViewModel>() {


    var dialog:AddCommentDialog? = null
    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : LikeListAdapter

    override fun getViewId(): Int = R.layout.activity_like
    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        bundle.apply {
            tvTitle.text = getString("title","")
        }

    }


    override fun onLifeCreated(owner: LifecycleOwner) {
       viewModel. repository.init(owner)
    }

    override fun observerUI() {

        viewModel.bean.observe(this, Observer {
            it?.apply {
                list.clear()
                list.addAll(this)
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {

        adapter  = LikeListAdapter(context,list)
        rvLike.adapter = adapter
        rvLike.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)



        viewModel.findAllLike()
    }

    override fun initListener() {


        adapter.setOnAddCommentListener {
            showCommentDialog(supportFragmentManager,it)
        }

        ivBack.setOnClickListener { finishAfterTransition() }

    }


    fun showCommentDialog(fragmentManager: FragmentManager, position: Int){
        if( dialog == null){
            dialog = AddCommentDialog()
        }
        val bundle = Bundle()
        bundle.putInt("position",position)
        dialog?.arguments = bundle
        dialog?.show(fragmentManager,"AddComment")
        dialog?.setOnAddCommentListner { pos, comment ->
            viewModel.addComment(list[pos].id,comment)
            dialog?.dismiss()
        }
    }

}
