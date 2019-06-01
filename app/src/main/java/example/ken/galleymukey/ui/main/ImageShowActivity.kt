package example.ken.galleymukey.ui.main

import android.animation.Animator
import android.graphics.Point
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.format.DateUtils
import android.util.Log
import android.view.*
import androidx.databinding.ViewDataBinding
import com.parallaxbacklayout.ParallaxBack
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_image_show.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import androidx.appcompat.widget.PopupMenu
import com.parallaxbacklayout.ViewDragHelper
import showmethe.github.kframework.util.system.DateUtil
import java.io.File
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import kotlinx.android.synthetic.main.item_hot.*








@ParallaxBack
class ImageShowActivity : BaseActivity<ViewDataBinding,MainViewModel>(){


    companion object{
        private val storeDir =
            Environment.getExternalStorageDirectory().path + File.separator+Environment.DIRECTORY_PICTURES+File.separator

    }
    
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
    private var count = 0;

    override fun getViewId(): Int = R.layout.activity_image_show

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        url = bundle.getString("photo","")
        id = bundle.getInt("id",-1)
        TGlide.load(url,image)
    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {
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


        image.setOnTouchListener { v, event ->

            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    if(event.pointerCount == 1){
                        count++
                        if(count == 1){
                            firstClick = System.currentTimeMillis()
                        }else if(count == 2){
                            secondClick = System.currentTimeMillis()
                            if(secondClick - firstClick <400){
                                if(id!=-1){
                                    showLike()
                                    viewModel.setLike(id,true)
                                }
                                count = 0
                            } else if(secondClick - firstClick in 401..1200){
                                finishAfterTransition()
                                count = 0
                            } else{
                                count = 0
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
                    if(Math.abs(offsetY)>screenHeight/2){
                        return@setOnTouchListener true
                    }
                    //移动
                    image.translationY = offsetY
                    image.translationX = event.rawX - dPoint.x

                    //缩放
                    image.scaleX = 1  - Math.abs(offsetY/screenHeight)
                    image.scaleY = 1  - Math.abs(offsetY/screenHeight)

                    //alpha
                    layout.translationY = -Math.abs(offsetY)
                    rlBg.alpha = 1- Math.abs(offsetY)/screenHeight
                }
                MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP->{
                    if(Math.abs(offsetY)>screenHeight/8){
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
        }
    }

    fun spacing(event: MotionEvent): Double{
        if(event.pointerCount ==  2){
            val x : Float = event.getX(0) - event.getX(1)
            val y : Float  = event.getY(0) - event.getY(1)
            return Math.sqrt((x * x + y * y).toDouble())
        }else{
            return 0.0
        }
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
