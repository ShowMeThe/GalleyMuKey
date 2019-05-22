package example.ken.galleymukey.ui.main

import android.graphics.Point
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.format.DateUtils
import android.util.Log
import android.view.Menu
import androidx.databinding.ViewDataBinding
import com.parallaxbacklayout.ParallaxBack
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_image_show.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import androidx.appcompat.widget.PopupMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.parallaxbacklayout.ViewDragHelper
import showmethe.github.kframework.util.system.DateUtil
import java.io.File
import android.view.MotionEvent




@ParallaxBack
class ImageShowActivity : BaseActivity<ViewDataBinding,MainViewModel>(){


    companion object{
        private val storeDir =
            Environment.getExternalStorageDirectory().path + File.separator+Environment.DIRECTORY_PICTURES+File.separator

    }

    private  var url = ""
    private var dPoint = PointF()
    var offsetY = 0f
    var layoutY = 0f
    var imageY = 0f

    override fun getViewId(): Int = R.layout.activity_image_show

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        url = bundle.getString("photo","")
        TGlide.load(url,image)
    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.fixToolbarScreen(this,toolbar)


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
                    imageY = image.y
                    dPoint.set(event.rawX,event.rawY)
                    layoutY = layout.y
                }
                MotionEvent.ACTION_MOVE ->{
                    val y = event.rawY
                    offsetY =  y - dPoint.y
                    if(Math.abs(offsetY)>screenHeight/6){
                        return@setOnTouchListener true
                    }
                    image.translationY = offsetY
                    layout.translationY = -Math.abs(offsetY)

                }
                MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP->{
                    if(Math.abs(offsetY)>screenHeight/10){
                        finishAfterTransition()
                    }else{
                        layout.y = layoutY
                        image.y = imageY
                    }

                }
            }
            true
        }



    }


    override fun onBackPressed() {
        finishAfterTransition()
    }

}
