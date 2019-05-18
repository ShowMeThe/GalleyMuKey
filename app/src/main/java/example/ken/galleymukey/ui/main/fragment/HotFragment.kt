package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentHotBinding
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_hot.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class HotFragment : BaseFragment<FragmentHotBinding, MainViewModel>() {




    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_hot

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {



    }

    override fun initListener() {
    }
}