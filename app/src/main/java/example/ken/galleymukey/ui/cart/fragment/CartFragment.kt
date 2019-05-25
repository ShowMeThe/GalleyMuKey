package example.ken.galleymukey.ui.cart.fragment

import android.os.Bundle
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentCartBinding
import example.ken.galleymukey.ui.main.vm.MainViewModel
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cart.fragment
 *
 * 2019/5/25
 **/
class CartFragment : BaseFragment<FragmentCartBinding, MainViewModel>() {

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cart

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

    }
}