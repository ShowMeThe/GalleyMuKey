package example.ken.galleymukey

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.router.constant.RdenConstant
import com.example.database.source.DataSourceBuilder
import com.example.database.source.Source
import com.example.database.source.dto.*

import com.example.main.ui.auth.vm.AuthViewModel
import com.example.main.ui.MainActivity
import com.example.main.ui.auth.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.extras.double2Decimal
import showmethe.github.kframework.util.rden.RDEN
import kotlin.random.Random

class SplashActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {
    override fun onLifeCreated(owner: LifecycleOwner) {

    }


    val random = Random(System.currentTimeMillis())
    val bannerList = ArrayList<String>()
    val source = Source.get()

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
            if(!RDEN.get(RdenConstant.hasAdd,false)){
                addBanner()
                addHotWall()
                addPhotoWall()
                addGoodsList()
                addHashTag()
                addCate()
                RDEN.put(RdenConstant.hasAdd,true)
            }

            delay(3000)
           /* startActivity(null,LikeActivity::class.java)*/
              if(!RDEN.get(RdenConstant.hasLogin,false)){
                  startActivity(null, LoginActivity::class.java)
            }else{
                  startActivity(null, MainActivity::class.java)
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
        DataSourceBuilder.getPhotoWall().delete()
       for(i in 0..random.nextInt(10,25)){
           val bean = PhotoWallDto()
           val list = ArrayList<String>()
           for(a in 0..random.nextInt(1,5)){
               list.add(source.getBanner()[(random.nextInt(0,28))])
           }
           bean.id = i
           bean.imageTop = list
           bean.avatar = source.getBanner()[(random.nextInt(0,28))]
           bean.username = source.getUserName()[(random.nextInt(0,13))] + source.getUserName()[(random.nextInt(6,13))]
           bean.like = (bean.username!!.contains("A") or bean.username!!.contains("e"))
           DataSourceBuilder.getPhotoWall().addPhotoBean(bean)
       }
    }

    fun addGoodsList(){
        DataSourceBuilder.getGoodsList().delete()
        for(i in 0..random.nextInt(60 , 80)){
            val bean = GoodsListDto()
            val list = ArrayList<String>()
            for(a in 0..random.nextInt(1,5)){
                list.add(source.getBanner()[(random.nextInt(0,28))])
            }
            bean.id = i
            bean.imageList = list
            bean.coverImg = list[0]
            bean.goodsDes = source.getContent()[random.nextInt(0,2)]
            bean.goodsName = source.getUserName()[(random.nextInt(0,13))] + source.getUserName()[(random.nextInt(6,13))]
            bean.firstType = random.nextInt(0,4)
            bean.price = double2Decimal(random.nextDouble(10.0, 30.0))
            bean.keyword = source.getHashTag()[random.nextInt(0,5)]
            DataSourceBuilder.getGoodsList().insertGoods(bean)

            val newGood = NewGoodsSellDto()
            newGood.keyId = bean.id
            newGood.hotSell = random.nextInt(20,90)/100f
            DataSourceBuilder.getNewGoodsDao().addBean(newGood)
        }
    }

    fun addHashTag(){
        for((index,hash) in source.getHashTag().withIndex()){
            val dto = HashTagDto()
            dto.keyword = hash
            dto.img = source.getBanner()[(random.nextInt(0,28))]
            DataSourceBuilder.getHashTag().insertHash(dto)
        }
    }

    fun addCate(){
        for(i in 0..random.nextInt(10,15)){
            val dto = CateDto()
            dto.keyword = source.getHashTag()[random.nextInt(0,5)]
            dto.img = source.getBanner()[(random.nextInt(0,28))]
            DataSourceBuilder.getCateDao().addCate(dto)
        }

    }



    override fun initListener() {



    }


}
