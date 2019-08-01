package com.example.router.dialog


import android.os.Bundle

import android.widget.TextView


import com.google.android.material.bottomsheet.BottomSheetBehavior


import com.example.database.bean.RegisterBean
import com.example.router.R
import com.example.router.databinding.DialogSignUpBinding

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_sign_up.*
import showmethe.github.kframework.dialog.SimpleBSheetDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.util.system.closeKeyboard

import java.util.concurrent.TimeUnit


/**
 *
 * com.example.router.dialog
 *
 * 2019/5/17
 **/

@WindowParam
class SignUpDialog   : SimpleBSheetDialogFragment() {


    private var mdDisposable: Disposable? = null

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_sign_up
        }.onView {
            it.onBindingView<DialogSignUpBinding> { binding ->
                binding?.apply {
                    val registerBean = RegisterBean()
                    bean = registerBean
                    executePendingBindings()


                    ivBack.setOnClickListener { hidden() }

                    tvCode.setOnClickListener {
                        start(tvCode,30,1)
                        context?.closeKeyboard(edCode)
                    }

                    btnReg.setOnClickListener {
                        onRegisterGet?.invoke(registerBean)
                    }


                }
            }
        }
    }

    fun start(tv: TextView,time: Int, interval: Int) {
        tv.isEnabled = false
        mdDisposable = Flowable.intervalRange(0, time.toLong(), 0, interval.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onCodeGet?.invoke(tv) }
            .doOnNext { aLong -> tv.text = "${(time - aLong!!)}s" }
            .doOnComplete {
                tv.isEnabled = true
                tv.text = "GET CODE"
                if (mdDisposable != null) {
                    mdDisposable!!.dispose()
                }
            }.subscribe()
    }



    var onRegisterGet : ((bean : RegisterBean)->Unit)? = null

    fun setOnRegisterGetListener(onRegisterGet : ((bean : RegisterBean)->Unit)){
        this.onRegisterGet = onRegisterGet
    }


    var onCodeGet : ((view : TextView)->Unit)? = null

    fun setOnCodeGetListener(onCodeGet : ((view : TextView)->Unit)){
        this.onCodeGet = onCodeGet
    }



    override fun onDestroyView() {
        if (mdDisposable != null) {
            mdDisposable!!.dispose()
        }
        super.onDestroyView()
    }


    fun hidden(){
        mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }



}