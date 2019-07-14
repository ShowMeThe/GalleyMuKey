package com.example.main.ui.mine.fragment

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.example.database.bean.RestPasswordBean
import com.example.main.R
import com.example.main.databinding.FragmentResetBinding

import com.example.main.ui.auth.LoginActivity
import com.example.main.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.fragment_reset.*
import showmethe.github.kframework.base.AppManager
import showmethe.github.kframework.base.BaseFragment
import showmethe.github.kframework.util.match.MD5

/**
 * com.example.main.ui.mine.fragment
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
                if(this>0){
                    showToast("Update Successfully")
                    context.startActivity(null,LoginActivity::class.java)
                    AppManager.get().finishAllWithoutTarget(LoginActivity::class.java)
                }
            }
        })

    }


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(this)
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