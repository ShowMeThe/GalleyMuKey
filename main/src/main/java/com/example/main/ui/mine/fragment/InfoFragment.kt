package com.example.main.ui.mine.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.main.R
import com.example.main.databinding.FragmentInfoBinding

import com.example.router.constant.RdenConstant


import com.example.main.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.ivHead
import showmethe.github.kframework.base.BaseFragment
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.widget.citypicker.picker.CityPicker

/**
 * com.example.main.ui.mine.fragment
 *
 * 2019/5/25
 **/
class InfoFragment : BaseFragment<FragmentInfoBinding, ProfileInfoViewModel>() {


    lateinit var  datePicker : DatePickerDialog
    val picker = CityPicker()

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


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(this)
    }


    override fun init(savedInstanceState: Bundle?) {
        TGlide.loadCirclePicture( RDEN.get(RdenConstant.avatar,""),ivHead)

        datePicker = DatePickerDialog(context,R.style.DatePickerTheme)


        viewModel.queryAccount(RDEN.get(RdenConstant.account,""))
    }

    override fun initListener() {

        tvUpdate.setOnClickListener {
            viewModel.switchToReset.value = true

        }

        llBirthday.setOnClickListener {
            datePicker.show()
        }

        datePicker.setOnDateSetListener { datePicker, year,month,day  ->
            viewModel.bean.value?.birthday =  "$year-${month+1}-$day"
        }


        llAddress.setOnClickListener {
            picker.show(context.supportFragmentManager,"picker")
        }

        picker.setOnCityPickListener { provinceBean, cityBean, districtBean ->
            viewModel.bean.value?.address =  "${provinceBean?.name}-${cityBean?.name}-${districtBean?.name}"
        }

    }
}