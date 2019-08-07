package com.example.router.dialog

import android.os.Bundle
import android.view.Gravity
import com.example.router.R

import kotlinx.android.synthetic.main.dialog_check_out.view.*
import showmethe.github.kframework.dialog.SimpleBSheetDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.util.toast.ToastFactory

@WindowParam(gravity = Gravity.BOTTOM,outSideCanceled = true,dimAmount = 0.0f)
class CheckOutDialog   : SimpleBSheetDialogFragment(){


    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_check_out
        }.onView {
            it.apply {
                btnCheck.setOnClickListener {
                    if( edPswd.text.toString() == "123456"){
                        onButtonCheck?.invoke()
                    }else{
                        ToastFactory.createToast("Please input correctly password")
                    }
                }
            }
        }
    }

    private var onButtonCheck :(()->Unit)? = null

    fun setOnButtonCheckListener(onButtonCheck :(()->Unit)){
        this.onButtonCheck = onButtonCheck
    }

}