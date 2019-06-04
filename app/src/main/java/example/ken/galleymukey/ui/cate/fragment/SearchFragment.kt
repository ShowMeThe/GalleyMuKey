package example.ken.galleymukey.ui.cate.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentSearchBinding
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_search

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

        viewModel.boolean.observe(this, Observer {
            it?.apply {


            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

    }
}