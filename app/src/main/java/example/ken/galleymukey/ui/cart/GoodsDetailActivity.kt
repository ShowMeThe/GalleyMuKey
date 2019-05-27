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
        StatusBarUtil.fixToolbarScreen(this,toolbar)
        StatusBarUtil.fixToolbar(this,toolbar2)

        list.apply {
            add("http://image1.xyzs.com/upload/a6/1c/1450580015244844/20151224/145089874795426_0.jpg")
            add("http://image2.xyzs.com/upload/9f/f3/1449368181406009/20151209/144960051320867_0.jpg")
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
                //折叠中
                fade.visibility = View.VISIBLE
            }else{
                //中间态
                val alpha = Math.abs(verticalOffset.toFloat()/appBarLayout.totalScrollRange.toFloat())
                ivCover.alpha =  alpha
                if(alpha <= 0.3f && verticalOffset>0){
                    fade.visibility = View.GONE
                }else if (alpha <= 1f && verticalOffset<0){
                    fade.visibility = View.GONE
                }
            }
        })



    }


}
