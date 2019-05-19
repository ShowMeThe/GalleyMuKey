package example.ken.galleymukey.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import example.ken.galleymukey.R
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.Source

import example.ken.galleymukey.source.dto.HotWallDto
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.PhotoWallDto
import example.ken.galleymukey.ui.auth.vm.AuthViewModel
import example.ken.galleymukey.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.widget.StatusBarUtil
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class SplashActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {



    val random = Random(System.currentTimeMillis())
    val bannerList = ArrayList<String>()
    val source = Source()

    override fun getViewId(): Int = R.layout.activity_splash

    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {
        random.nextInt(0,10)
        source.init()

        GlobalScope.launch(Dispatchers.Main) {
            addBanner()
            addHotWall()
            addPhotoWall()
            delay(3000)
            if(RDEN.get(RdenConstant.hasLogin,false)!!){
                startActivity(null,LoginActivity::class.java)
            }else{
                startActivity(null,MainActivity::class.java)
            }
            finish()
        }


    }

    fun addBanner(){

        val bean  = ImageUrlDto()
        for(i in 0..random.nextInt(2,7)){
            bannerList.add(source.getBanner()[(random.nextInt(0,28))])
        }
        bean.arrarys = bannerList
        bean.key = "LoginBanner"
        DataSourceBuilder.getImageDao().addImages(bean)
    }

    fun addHotWall(){
        DataSourceBuilder.getHotWall().deleteAll()
        val num =  10 * (random.nextInt(1,5))
        for(i in 0 until num){
            val bean = HotWallDto()
            when(i%10){
                0,6->{
                    bean.imageTop = source.getBanner()[(random.nextInt(0,28))]
                    bean.type = 1
                }
                1,2,3,4,5,7,8,9->{
                    bean.imageTop = source.getBanner()[(random.nextInt(0,28))]
                    bean.imageBottom = source.getBanner()[(random.nextInt(0,28))]
                    bean.type = 0
                }
            }
            DataSourceBuilder.getHotWall().addHotBean(bean)
        }
    }


    fun addPhotoWall(){
       for(i in 0..random.nextInt(5,20)){
           val bean = PhotoWallDto()
           val list = ArrayList<String>()
           for(a in 0..random.nextInt(1,5)){
               list.add(source.getBanner()[(random.nextInt(0,28))])
           }
           bean.id = i
           bean.imageTop = list
           bean.avatar = source.getBanner()[(random.nextInt(0,28))]
           bean.username = source.getUserName()[(random.nextInt(0,13))] + source.getUserName()[(random.nextInt(6,13))]
           DataSourceBuilder.getPhotoWall().addPhotoBean(bean)
       }
    }


    override fun initListener() {



    }


}
