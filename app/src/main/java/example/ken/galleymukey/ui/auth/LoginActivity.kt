package example.ken.galleymukey.ui.auth

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.commit451.nativestackblur.NativeStackBlur
import example.ken.galleymukey.R
import example.ken.galleymukey.dialog.LoginDialog
import example.ken.galleymukey.ui.auth.vm.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.BlurImageTransform
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.commbine.LruCacheUtil
import showmethe.github.kframework.util.widget.StatusBarUtil
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.R.attr.bitmap
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.drawable.BitmapDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import example.ken.galleymukey.dialog.SignUpDialog
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList


class LoginActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {

    val dialog by lazy {  LoginDialog() }
    val signUpDialog by lazy { SignUpDialog() }

    val random = ThreadLocalRandom.current();
    var snackbar : Snackbar? = null

    override fun showCreateReveal(): Boolean = true
    override fun getViewId(): Int = R.layout.activity_login
    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setFullScreen(this)
        addBanner()





    }

    override fun initListener() {

        btnLogin.setOnClickListener { showLoginDialog() }

        btnReg.setOnClickListener { showSignUpDialog() }

        signUpDialog.setOnCodeGetListener {
            showSnack(it)
        }

    }


    fun showSnack(view : View){
        val num = random.nextInt(1000,9999)
        snackbar =  Snackbar.make(view,"${num}",15000).setAction("copy") {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager;
            val clipData = ClipData.newPlainText("text","${num}")
            clipboard.primaryClip = clipData
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


    fun addBanner(){
        val list = ArrayList<String>()
        list.add("http://image1.xyzs.com/upload/a6/1c/1450580015244844/20151224/145089874795426_0.jpg")
        list.add("http://image2.xyzs.com/upload/9f/f3/1449368181406009/20151209/144960051320867_0.jpg")
        list.add("http://image3.xyzs.com/upload/b9/40/1449104703418440/20151205/144925600471264_0.jpg")
        list.add("http://image1.xyzs.com/upload/9b/b2/1450314878985387/20151219/145046718515607_0.jpg")
        list.add("http://image2.xyzs.com/upload/fc/6a/1450315960904658/20151219/145046718037409_0.jpg")

        banner.addList(list)
        banner.setOnImageLoader { url, imageView ->
            TGlide.load(url, imageView)
        }
        banner.play()
    }

}
