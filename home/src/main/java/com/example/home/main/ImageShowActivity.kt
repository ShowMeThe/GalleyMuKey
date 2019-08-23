package com.example.home.main

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Intent
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
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.home.R
import com.example.home.databinding.ActivityImageShowBinding
import com.example.home.main.adapter.ImageViewAdapter
import com.example.home.main.vm.HomeViewModel
import java.text.FieldPosition
import kotlin.math.abs
import kotlin.math.sqrt



class ImageShowActivity : BaseActivity<ActivityImageShowBinding, HomeViewModel>(){

    companion object{

        fun startImg(context:BaseActivity<*,*>,path:String,transitionView:View){
            val intent = Intent(context,ImageShowActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("type",1)
            bundle.putString("content",path)
            val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(context, transitionView, "photo")
            intent.putExtras(bundle)
            context. startActivity(intent, transitionActivityOptions.toBundle())
        }

        fun startImgs(context: BaseActivity<*, *>,paths:ArrayList<String>,position: Int){
            val intent = Intent(context,ImageShowActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("type",2)
            bundle.putStringArrayList("content",paths)
            bundle.putInt("position",position)
            intent.putExtras(bundle)
            context. startActivity(intent)
        }

    }


    /**
     * 保存路径
     */
    private var storeDir = ""
    private  var path = ""
    private var position = 0
    private lateinit var adapter:ImageViewAdapter
    private val list = ObservableArrayList<String>()

    override fun getViewId(): Int = R.layout.activity_image_show

    override fun initViewModel(): HomeViewModel = createViewModel(HomeViewModel::class.java)


    override fun onLifeCreated(owner: LifecycleOwner) {


    }
    override fun onBundle(bundle: Bundle) {
        when(bundle.getInt("type",1)){
            1 ->  {
                path = bundle.getString("content","")
                list.add(path)
            }
            2 -> {
                list.addAll(bundle.getStringArrayList("content")!!)
                position = bundle.getInt("position",0)
            }
        }



    }

    override fun observerUI() {


    }

    override fun setTheme() {
        StatusBarUtil.fixToolbarScreen(this,toolbar)
        StatusBarUtil.setTranslucentNavigation(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath


        adapter = ImageViewAdapter(context,list)
        vp2.adapter = adapter
        vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vp2.setCurrentItem(position,true)

    }


    override fun initListener() {

        ivBack.setOnClickListener {
            finishAfterTransition()
        }


        adapter.setOnDownProgressListener { offsetY, onComplete ->
            //alpha
            layout.translationY = -abs(offsetY)
            rlBg.alpha = 1- abs(offsetY) /screenHeight
        }


        adapter.setOnDownCompleteListener {
            if(it){
                finishAfterTransition()
            }
        }


        ivMenu.setOnClickListener { bean ->
            val popup = PopupMenu(this, bean)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pre_view_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId){
                    R.id.save_item ->{
                        TGlide.saveInDir(storeDir, "${System.currentTimeMillis()}",list[vp2.currentItem]){
                            showToast("Save at $it")
                        }
                    }
                }
                false
            }
            popup.show()
        }

        



    }



    override fun onBackPressed() {
        finishAfterTransition()
    }

}
