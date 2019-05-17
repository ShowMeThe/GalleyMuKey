package example.ken.galleymukey.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import example.ken.galleymukey.R
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.ui.auth.vm.AuthViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.widget.StatusBarUtil

class SplashActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {


    override fun getViewId(): Int = R.layout.activity_splash

    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {


        GlobalScope.launch(Dispatchers.Main) {
            addBanner()
            delay(3000)
            startActivity(null,LoginActivity::class.java)
            finish()
        }


    }

    fun addBanner(){
        val list = ArrayList<String>()
        list.add("http://image1.xyzs.com/upload/a6/1c/1450580015244844/20151224/145089874795426_0.jpg")
        list.add("http://image2.xyzs.com/upload/9f/f3/1449368181406009/20151209/144960051320867_0.jpg")
        list.add("http://image3.xyzs.com/upload/b9/40/1449104703418440/20151205/144925600471264_0.jpg")
        list.add("http://image1.xyzs.com/upload/9b/b2/1450314878985387/20151219/145046718515607_0.jpg")
        list.add("http://image2.xyzs.com/upload/fc/6a/1450315960904658/20151219/145046718037409_0.jpg")

        val bean  = ImageUrlDto()
        bean.arrarys = list
        bean.key = "LoginBanner"
        DataSourceBuilder.getImageDao().addImages(bean)
    }


    override fun initListener() {



    }


}
