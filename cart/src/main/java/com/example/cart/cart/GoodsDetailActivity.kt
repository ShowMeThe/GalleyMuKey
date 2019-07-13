package com.example.cart.cart

import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.transition.Transition
import com.example.cart.R
import com.google.android.material.appbar.AppBarLayout

import com.example.database.source.dto.GoodsListDto

import com.example.cart.cart.vm.GoodsViewModel
import com.example.cart.databinding.ActivityGoodsDetailBinding
import kotlinx.android.synthetic.main.activity_goods_detail.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.BitmapTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import kotlin.math.abs

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

    override fun onLifeCreated(owner: LifecycleOwner) {

    }

    override fun observerUI() {

        viewModel.boolean.observe(this, Observer {
            it?.apply {
                if(this){
                    startActivity(null,CartActivity::class.java)
                }
            }
        })

    }

    override fun setTheme() {
        StatusBarUtil.setFullScreen(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            bean = dto
            executePendingBindings()
            banner.addList(dto!!.imageList!!)
            banner.setOnImageLoader { url, imageView ->
                TGlide.load(url, imageView)
            }
            banner.play()
        }

    }

    override fun initListener() {

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                //展开
                ivCover.alpha = 0f
            }else if(abs(verticalOffset) >= appBarLayout.totalScrollRange){
                //折叠
                ivCover.alpha = 1f
            }else{
                //中间态
                val alpha = abs(verticalOffset.toFloat()/appBarLayout.totalScrollRange.toFloat())
                ivCover.alpha  = if(alpha<0.25) 0f else (alpha*1.5).toFloat()
            }
        })


        ivBack.setOnClickListener {
            finishAfterTransition()
        }

        btnBuy.setOnClickListener {
             viewModel.addCartBean(this.dto!!.id)
        }

    }



    override fun onBackPressed() {
        finishAfterTransition()
    }

}
