package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import android.util.Log
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

/**
 * example.ken.galleymukey.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class PhotoFragment : BaseFragment<FragmentPhotoBinding, MainViewModel>() {



    val list = ObservableArrayList<PhotoWallBean>()
    lateinit var adapter : PhotoAdapter
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

        adapter.setOnPhotoClickListener { view, url ->
            val bundle = Bundle()
            bundle.putString("photo",url)
            context.startActivity(bundle, ImageShowActivity::class.java,view,"photo")
        }
        adapter.setOnLikeClickListener { id, like ->
            viewModel.setLike(id, like)
        }

    }
}