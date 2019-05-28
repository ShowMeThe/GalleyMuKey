package example.ken.galleymukey.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.cart.vm.GoodsViewModel
import kotlinx.android.synthetic.main.activity_goods_detail.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.base.BaseViewModel
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil

class GoodsDetailActivity : BaseActivity<ViewDataBinding, GoodsViewModel>() {

    val list = ObservableArrayList<String>()

    override fun getViewId(): Int = R.layout.activity_goods_detail
    override fun initViewModel(): GoodsViewModel =createViewModel(GoodsViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setFullScreen(this)

        list.apply {
            add("http://image1.xyzs.com/upload/6d/7a/1450150493535765/20151217/145028979798709_0.jpg")
            add("http://image4.xyzs.com/upload/03/f7/1449978315650125/20151217/145028981364776_0.jpg")
            add("http://image4.xyzs.com/upload/03/f7/1449978315650125/20151217/145028981364776_0.jpg")
            add("http://image1.xyzs.com/upload/b2/88/1450055515760915/20151217/145028981024042_0.jpg")
        }
        banner.addList(list)
        banner.setOnImageLoader { url, imageView ->
            TGlide.loadNoCrop(url, imageView)
        }
        banner.play()

    }

    override fun initListener() {

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                //展开
            }else if(Math.abs(verticalOffset) >= appBarLayout.totalScrollRange){
                //折叠

            }else{
                //中间态
                val alpha = Math.abs(verticalOffset.toFloat()/appBarLayout.totalScrollRange.toFloat())
                ivCover.alpha  = (alpha*1.5).toFloat()
            }
        })




    }



    override fun onBackPressed() {
        finishAfterTransition()
    }

}
