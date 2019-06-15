package example.ken.galleymukey.ui.auth

import android.animation.Animator
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import example.ken.galleymukey.R
import example.ken.galleymukey.dialog.LoginDialog
import example.ken.galleymukey.ui.auth.vm.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import android.content.ClipData
import android.content.ClipboardManager
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.bean.RegisterBean
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.dialog.SignUpDialog
import example.ken.galleymukey.ui.MainActivity
import showmethe.github.kframework.util.ClipboardUtil
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.system.KeyBoardUtils
import java.util.concurrent.ThreadLocalRandom


class LoginActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {

    val dialog by lazy {  LoginDialog() }
    val signUpDialog by lazy { SignUpDialog() }

    val random = ThreadLocalRandom.current()
    var snackbar : Snackbar? = null
    var num = 0


    override fun showCreateReveal(): Boolean = true
    override fun getViewId(): Int = R.layout.activity_login
    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun addLifecycle(lifecycle: Lifecycle) {
        viewModel.repository.init(this)
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
            KeyBoardUtils.hideSoftKeyboard(this)
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
        if(it.account!!.isEmpty()){
            showToast("Please input username")
           return false
        }else if(it.password!!.isEmpty()){
            showToast("Please input password")
            return false
        } else if(it.code!!.isEmpty() || !it.code!!.equals(num.toString())){
            showToast("Please input correctly code")
            return false
        }
        return true
    }




    fun showSnack(view : TextView){
        KeyBoardUtils.hideSoftKeyboard(this)
        num = random.nextInt(1000,9999)
        snackbar =  Snackbar.make(view,"${num}",15000)
            .setBackgroundTint(ContextCompat.getColor(context,R.color.color_ff6e00))
            .setTextColor(ContextCompat.getColor(context,R.color.white))
            .setActionTextColor(ContextCompat.getColor(context,R.color.white))
            .setAction("copy") {
            ClipboardUtil.copyTextToClipboard(context,"${num}")
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
