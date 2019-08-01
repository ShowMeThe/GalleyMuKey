package com.example.router.dialog

import android.os.Bundle
import android.view.Gravity
import com.example.database.bean.LoginBean
import com.example.router.R
import com.example.router.constant.RdenConstant
import com.example.router.databinding.DialogLoginBinding
import kotlinx.android.synthetic.main.dialog_login.view.*
import showmethe.github.kframework.dialog.SimpleDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.util.rden.RDEN

@WindowParam(gravity = Gravity.CENTER,canceled = true,outSideCanceled = true,dimAmount = 0.0f)
class LoginDialog : SimpleDialogFragment() {
    
    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_login
        }.onView { view ->
            view.onBindingView<DialogLoginBinding> {
                val loginBean= LoginBean()
                it?.apply {
                    bean = loginBean
                    executePendingBindings()

                    root.apply {

                        loginBean.account = RDEN.get(RdenConstant.account,"")

                        ivClose.setOnClickListener { dialog?.dismiss() }

                        btnLogin.setOnClickListener { onLoginGet?.invoke(loginBean) }

                    }

                }
            }
        }
    }

    var onLoginGet : ((bean : LoginBean)->Unit)? = null

    fun setOnLoingGetListener(onLoginGet : ((bean : LoginBean)->Unit)){
        this.onLoginGet = onLoginGet
    }

}