package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.databinding.FragmentHotBinding
import example.ken.galleymukey.ui.main.ImageShowActivity
import example.ken.galleymukey.ui.main.adapter.HotAdapter
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_hot.*
import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.main.fragment
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