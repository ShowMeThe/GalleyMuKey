package example.ken.galleymukey.ui.mine.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.RestPasswordBean
import example.ken.galleymukey.databinding.FragmentResetBinding
import example.ken.galleymukey.ui.auth.LoginActivity
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.fragment_reset.*
import showmethe.github.kframework.base.AppManager
import showmethe.github.kframework.base.BaseFragment
import showmethe.github.kframework.util.match.MD5

/**
 * example.ken.galleymukey.ui.mine.fragment
 *
 * 2019/5/25
 **/
class ResetFragment  : BaseFragment<FragmentResetBinding, ProfileInfoViewModel>() {

    val restPasswordBean = RestPasswordBean()
    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_reset

    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {

        viewModel.updatePswd.observe(this, Observer {
            it?.apply {
                showToast("Update Successfully")
                context.startActivity(null,LoginActivity::class.java)
                AppManager.get().finishAllWithoutTarget(LoginActivity::class.java)
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {

        binding?.apply {
            bean = restPasswordBean
            executePendingBindings()

        }

    }

    override fun initListener() {

        btnSubmit.setOnClickListener {
            viewModel.updatePassword(MD5.string2MD5(restPasswordBean.newPassword))
        }

    }
}