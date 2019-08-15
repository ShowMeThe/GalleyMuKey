package com.example.home.main

import android.animation.Animator
import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import showmethe.github.kframework.parallaxback.ParallaxBack


import kotlinx.android.synthetic.main.activity_image_show.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import androidx.appcompat.widget.PopupMenu
import android.view.animation.AccelerateInterpolator
import androidx.lifecycle.LifecycleOwner
import com.example.home.R
import com.example.home.databinding.ActivityImageShowBinding
import com.example.home.main.vm.HomeViewModel
import kotlin.math.abs
import kotlin.math.sqrt


@ParallaxBack
class ImageShowActivity : BaseActivity<ActivityImageShowBinding, HomeViewModel>(){

    /**
     * 保存路径
     */
    private var storeDir = ""
    private  var url = ""
    private var id = -1
    private var dPoint = PointF()
    private var minHeight= 0
    private var offsetY = 0f
    private var layoutY = 0f
    private var imageX = 0f
    private var imageY = 0f
    private var firstClick: Long = 0//第一次点击时间
    private var secondClick: Long = 0//第二次点击时间
    private var count = 0


    override fun getViewId(): Int = R.layout.activity_image_show

    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)


    override fun onLifeCreated(owner: LifecycleOwner) {


    }
    override fun onBundle(bundle: Bundle) {
        url = bundle.getString("photo","")
        id = bundle.getInt("id",-1)
        TGlide.load(url,image)
    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {
        storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath

        StatusBarUtil.fixToolbarScreen(this,toolbar)
        StatusBarUtil.setTranslucentNavigation(this)




    }


    override fun initListener() {

        ivBack.setOnClickListener {
            finishAfterTransition()
        }


        ivMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pre_view_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId){
                    R.id.save_item ->{
                        TGlide.saveInDir(storeDir, "${System.currentTimeMillis()}",url){
                            showToast("Save at $it")
                        }
                    }
                }
                false
            }
            popup.show()
        }

        
        image.setOnDownProgressListener { offsetY, onComplete ->
            //alpha
            layout.translationY = -abs(offsetY)
            rlBg.alpha = 1- abs(offsetY) /screenHeight
            if(onComplete){
                finishAfterTransition()
            }
        }


        /*image.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    if(event.pointerCount == 1){
                        count++
                        if(count == 1){
                            firstClick = System.currentTimeMillis()
                        }else if(count == 2){
                            secondClick = System.currentTimeMillis()
                            count = when {
                                secondClick - firstClick <400 -> {
                                    if(id!=-1){
                                        showLike()
                                        viewModel.setLike(id,true)
                                    }
                                    0
                                }
                                secondClick - firstClick in 401..1200 -> {
                                    finishAfterTransition()
                                    0
                                }
                                else -> 0
                            }
                        }
                        layoutY = layout.y
                        imageX = image.x
                        imageY = image.y
                        dPoint.set(event.rawX,event.rawY)
                    }


                }
                MotionEvent.ACTION_MOVE ->{
                    val y = event.rawY
                    offsetY =  y - dPoint.y
                    if(abs(offsetY) >screenHeight/2){
                        return@setOnTouchListener true
                    }
                    //移动
                    image.translationY = offsetY
                    image.translationX = event.rawX - dPoint.x

                    //缩放
                    image.scaleX = 1  - abs(offsetY/screenHeight)
                    image.scaleY = 1  - abs(offsetY/screenHeight)

                    //alpha
                    layout.translationY = -abs(offsetY)
                    rlBg.alpha = 1- abs(offsetY) /screenHeight
                }
                MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP->{
                    if(abs(offsetY) >screenHeight/8){
                        finishAfterTransition()
                    }else{
                        //恢复原位
                        layout.y = layoutY
                        image.y = imageY
                        image.x = imageX
                        image.scaleX = 1f
                        image.scaleY = 1f
                    }
                }
            }
            true
        }*/




    }



    fun showLike(){
        ivLike.animate().alpha(0.8f)
            .setInterpolator(AccelerateInterpolator())
            .setDuration(700).setListener(object :Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    ivLike.visibility = View.INVISIBLE
                }
                override fun onAnimationCancel(animation: Animator?) {
                }
                override fun onAnimationStart(animation: Animator?) {
                    ivLike.visibility = View.VISIBLE
                    ivLike.setLike(true,true)
                }
            }).start()
    }


    override fun onBackPressed() {
        finishAfterTransition()
    }

}
