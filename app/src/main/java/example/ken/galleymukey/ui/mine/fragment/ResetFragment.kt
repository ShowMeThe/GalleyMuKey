package example.ken.galleymukey.ui.mine.fragment

import android.os.Bundle
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentResetBinding
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.mine.fragment
 *
 * 2019/5/25
 **/
class ResetFragment  : BaseFragment<FragmentResetBinding, ProfileInfoViewModel>() {

    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_reset

    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initListener() {


    }
}