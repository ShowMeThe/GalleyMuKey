package com.example.router.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.router.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import kotlinx.android.synthetic.main.dialog_check_out.view.*
import showmethe.github.kframework.dialog.SimpleBSheetDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.util.ToastFactory

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