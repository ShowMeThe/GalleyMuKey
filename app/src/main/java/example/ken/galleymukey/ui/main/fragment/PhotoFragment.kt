package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.view.menu.MenuBuilder

import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.FragmentPhotoBinding
import example.ken.galleymukey.ui.main.ImageShowActivity
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_galley.*
import kotlinx.android.synthetic.main.fragment_photo.*
import showmethe.github.kframework.base.BaseFragment
import androidx.appcompat.app.AppCompatActivity
import example.ken.galleymukey.dialog.SeeCommentDialog


/**
 * example.ken.galleymukey.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class PhotoFragment : BaseFragment<FragmentPhotoBinding, MainViewModel>() {



    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : PhotoAdapter
    val dialog  = SeeCommentDialog()
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
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
                    dialog.list.clear()
                    dialog.list.addAll(this)
                    dialog.show(childFragmentManager,"dialog")
                }
            }
        })


    }


    override fun init(savedInstanceState: Bundle?) {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)


        adapter = PhotoAdapter(context,list)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

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