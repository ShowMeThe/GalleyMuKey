package example.ken.galleymukey.ui.main

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.ImageViewBindingAdapter
import androidx.databinding.adapters.LinearLayoutBindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.dialog.AddCommentDialog
import example.ken.galleymukey.source.Source
import example.ken.galleymukey.ui.main.adapter.ImageShowAdapter
import example.ken.galleymukey.ui.main.adapter.LikeListAdapter
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_like.*
import kotlinx.android.synthetic.main.include_title.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.widget.transformer.CardStackTransformer

class LikeActivity : BaseActivity<ViewDataBinding,MainViewModel>() {


    var dialog:AddCommentDialog? = null
    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : LikeListAdapter

    override fun getViewId(): Int =R.layout.activity_like
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        bundle.apply {
            tvTitle.text = getString("title","")
        }

    }


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.findAllLike()
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
        dialog?.show(supportFragmentManager,"AddComment")
        dialog?.setOnAddCommentListner { pos, comment ->
            viewModel.addComment(list[pos].id,comment)
            dialog?.dismiss()
        }
    }

}
