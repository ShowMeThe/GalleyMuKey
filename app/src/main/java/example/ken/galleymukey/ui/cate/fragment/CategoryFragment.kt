package example.ken.galleymukey.ui.cate.fragment

import android.os.Bundle
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentCategoryBinding
import example.ken.galleymukey.ui.main.vm.MainViewModel
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class CategoryFragment : BaseFragment<FragmentCategoryBinding, MainViewModel>() {

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_category

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

    }
}