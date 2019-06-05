package example.ken.galleymukey.ui.cart

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ActivityGoodsDetailBinding
import example.ken.galleymukey.source.dto.GoodsListDto
import example.ken.galleymukey.ui.cart.vm.GoodsViewModel
import kotlinx.android.synthetic.main.activity_goods_detail.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.base.BaseViewModel
import showmethe.github.kframework.glide.BitmapTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil

class GoodsDetailActivity : BaseActivity<ActivityGoodsDetailBinding, GoodsViewModel>() {

    val list = ObservableArrayList<String>()
    var dto : GoodsListDto? = null

    override fun getViewId(): Int = R.layout.activity_goods_detail
    override fun initViewModel(): GoodsViewModel =createViewModel(GoodsViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        dto = bundle.getSerializable("dto") as GoodsListDto?
        val defaultColor = ContextCompat.getColor(context,R.color.color_ff6e00)
        dto?.apply {
            TGlide.loadIntoBitmap(coverImg,object : BitmapTarget(){
                override fun resourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate {
                        it?.apply {
                            tvDes.setTextColor(getVibrantColor(defaultColor))
                            btnBuy.setBackgroundColor(getVibrantColor(defaultColor))
                        }
                    }
                }
            })
        }
    }

    override fun observerUI() {
        binding?.apply {
            bean = dto
            banner.addList(dto!!.imageList!!)
            banner.setOnImageLoader { url, imageView ->
                TGlide.loadNoCrop(url, imageView)
            }
            banner.play()
            executePendingBindings()
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setFullScreen(this)


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


        ivBack.setOnClickListener {
            finishAfterTransition()
        }

    }



    override fun onBackPressed() {
        finishAfterTransition()
    }

}
