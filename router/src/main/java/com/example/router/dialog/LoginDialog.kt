package com.example.router.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import com.example.database.bean.LoginBean
import com.example.router.R
import com.example.router.constant.RdenConstant
import com.example.router.databinding.DialogLoginBinding

import kotlinx.android.synthetic.main.dialog_login.view.*
import showmethe.github.kframework.util.rden.RDEN


/**
 * com.example.router.dialog
 * cuvsu
 * 2019/5/16
 **/
class LoginDialog : DialogFragment() {

    lateinit var mContext: Context
    private var binding : DialogLoginBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_login, null)
        binding = DataBindingUtil.bind(view)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)


        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setDimAmount(0.0f)
                setLayout(dm.widthPixels, window.attributes.height)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(Gravity.CENTER)
                setWindowAnimations(R.style.LeftRightAnim)
            }
        }

        val loginBean= LoginBean()
        view?.apply {

            loginBean.account = RDEN.get(RdenConstant.account,"")
            binding?.apply {
                bean = loginBean
                executePendingBindings()
            }


            ivClose.setOnClickListener { dialog.dismiss() }

            btnLogin.setOnClickListener { onLoginGet?.invoke(loginBean) }


        }


        return dialog
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogDismiss?.invoke()
    }



    var onLoginGet : ((bean : LoginBean)->Unit)? = null

    fun setOnLoingGetListener(onLoginGet : ((bean : LoginBean)->Unit)){
        this.onLoginGet = onLoginGet
    }



    var onDialogDismiss : (()->Unit)? = null

    fun setOnDialogDismissListener (onDialogDismiss : (()->Unit)){
        this.onDialogDismiss = onDialogDismiss
    }


}