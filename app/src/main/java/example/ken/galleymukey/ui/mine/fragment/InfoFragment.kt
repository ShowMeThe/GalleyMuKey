package example.ken.galleymukey.ui.mine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import example.ken.galleymukey.R
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.databinding.FragmentInfoBinding

import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.include_title_bar.*
import showmethe.github.kframework.base.BaseFragment
import showmethe.github.kframework.dialog.CalendarDialogFragment
import showmethe.github.kframework.util.rden.RDEN

/**
 * example.ken.galleymukey.ui.mine.fragment
 *
 * 2019/5/25
 **/
class InfoFragment : BaseFragment<FragmentInfoBinding, ProfileInfoViewModel>() {

    val dialog = CalendarDialogFragment()

    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_info
    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {

        viewModel.bean.observe(this, Observer {
                binding?.apply {
                    it?.apply {
                    bean = this
                    executePendingBindings()
                }
            }
        })

        viewModel.updateController.observe(this, Observer {
            it?.apply {
                viewModel.updateInfo()
            }
        })

        viewModel.updateInfo.observe(this, Observer {
            it?.apply {
                if(this>0){
                    showToast("Update Successfully")
                }
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.queryAccount(RDEN.get(RdenConstant.account,""))

    }

    override fun initListener() {

        tvUpdate.setOnClickListener {
            viewModel.switchToReset.value = true

        }

        llBirthday.setOnClickListener {
            dialog.show(childFragmentManager,"CalendarDialogFragment")
        }

        dialog.setOnDatePickDialogListener { day, month, year ->
            viewModel.bean.value?.birthday =  "$year-$month-$day"
        }

    }
}