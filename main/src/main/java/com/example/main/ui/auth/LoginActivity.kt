package com.example.main.ui.auth

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.main.ui.auth.vm.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.example.database.bean.LoginBean
import com.example.database.bean.RegisterBean
import com.example.main.R
import com.example.router.constant.RdenConstant
import com.example.router.dialog.SignUpDialog
import com.example.main.ui.MainActivity
import com.example.router.dialog.LoginDialog
import showmethe.github.kframework.util.copyTextToClipboard

import showmethe.github.kframework.util.rden.RDEN

import showmethe.github.kframework.util.system.hideSoftKeyboard
import kotlin.random.Random


class LoginActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {


    val dialog by lazy {  LoginDialog() }
    val signUpDialog by lazy { SignUpDialog() }

    val random = Random(System.currentTimeMillis())
    var snackbar : Snackbar? = null
    var num = 0


    override fun showCreateReveal(): Boolean = true
    override fun getViewId(): Int = R.layout.activity_login
    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }
    override fun onLifeCreated(owner: LifecycleOwner) {


    }


    override fun observerUI() {

        viewModel.result.observe(this,androidx.lifecycle.Observer {
            it?.apply {
                if(this){
                    showToast("Sign up Successful")
                    signUpDialog.dismiss()
                }
            }
        })

        viewModel.auth.observe(this,androidx.lifecycle.Observer {
            it?.apply {
                showToast("Login Successful")
                RDEN.put(RdenConstant.account,it.account!!)
                RDEN.put(RdenConstant.hasLogin,true)
                RDEN.put(RdenConstant.avatar,avatar)
                RDEN.put(RdenConstant.userId,userId)
                dialog.dismiss()
                startActivity(null,MainActivity::class.java)
                finishAfterTransition()
            }
        })

        viewModel.bannerList.observe(this,androidx.lifecycle.Observer {
            it?.apply {
                banner.addList(it)
                banner.setOnImageLoader { url, imageView -> TGlide.load(url, imageView) }
                banner.play()
            }
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setFullScreen(this)


        viewModel.getBanner("LoginBanner")



    }

    override fun initListener() {

        btnLogin.setOnClickListener { showLoginDialog() }

        btnReg.setOnClickListener { showSignUpDialog() }

        signUpDialog.setOnCodeGetListener {
            hideSoftKeyboard()
            showSnack(it)
        }

        signUpDialog.setOnRegisterGetListener {
           if(checkReg(it)){
               viewModel.register(it)
           }
        }

        dialog.setOnLoingGetListener {
            if(checkLogin(it)){
                viewModel.login(it)
            }
        }

    }


    fun checkLogin(it : LoginBean) : Boolean{
        if(it.account!!.isEmpty()){
            showToast("Please input username")
            return false
        }else if(it.password!!.isEmpty()){
            showToast("Please input password")
            return false
        }
        return true
    }


    fun checkReg(it : RegisterBean) : Boolean{

        if(it.account.isEmpty()){
            showToast("Please input username")
           return false
        }else if(it.password.isEmpty()){
            showToast("Please input password")
            return false
        } else if(it.code.isEmpty() || it.code != num.toString()){
            showToast("Please input correctly code")
            return false
        }
        return true
    }




    fun showSnack(view : TextView){
        hideSoftKeyboard()
        num = random.nextInt(200,9999)
        snackbar =  Snackbar.make(view,"${num}",15000)
            .setBackgroundTint(ContextCompat.getColor(context,R.color.color_ff6e00))
            .setTextColor(ContextCompat.getColor(context,R.color.white))
            .setActionTextColor(ContextCompat.getColor(context,R.color.white))
            .setAction("copy") {
            copyTextToClipboard(context,"${num}")
            showToast("Copy successfully")
            snackbar!!.dismiss()
        }
        snackbar!!.show()
    }



    fun showSignUpDialog(){
        supportFragmentManager.executePendingTransactions()
        if(!signUpDialog.isAdded){
            signUpDialog.show(supportFragmentManager,"")
        }
    }


    fun showLoginDialog(){
        supportFragmentManager.executePendingTransactions()
        if(!dialog.isAdded){
            dialog.show(supportFragmentManager,"")
        }

    }




}
