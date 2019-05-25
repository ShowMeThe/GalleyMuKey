package example.ken.galleymukey.ui.mine.fragment

import android.os.Bundle
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentInfoBinding
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.fragment_info.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.mine.fragment
 *
 * 2019/5/25
 **/
class InfoFragment : BaseFragment<FragmentInfoBinding, ProfileInfoViewModel>() {

    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_info
    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {


    }

    override fun initListener() {

        tvUpdate.setOnClickListener {
            viewModel.switchToReset.value = true
        }


    }
}