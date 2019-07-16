package showmethe.github.kframework.util.widget

import android.app.Activity
import android.content.Context
import android.os.Build
import com.google.android.material.tabs.TabLayout
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.DrawableRes
import showmethe.github.kframework.glide.TGlide

import java.lang.reflect.Field

/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/9/18
 * Time : 15:35
 */


fun View.setOnSingleClickListner(onSingleClick : (it:View)->Unit){
    var lastClickTime = 0L
    val lastLongTime = 2000L

    setOnClickListener {
        val time = System.currentTimeMillis()
        if(time - lastClickTime > lastLongTime){
            onSingleClick.invoke(it)
            lastClickTime = time
        }
    }
}

fun ImageView.load(resource:Any){
    TGlide.load(resource,this)
}

fun ImageView.loadBackground(resource:Any){
    TGlide.loadInBackground(resource,this)
}


/**
 * 反射获取mLayoutScreen 设置对象为true,全屏效果，覆盖状态栏
 * @param needFullScreen
 */
fun fitPopWindowOverStatusBar(mPopupWindow: PopupWindow?, needFullScreen: Boolean) {
    try {
        val mLayoutInScreen = PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
        mLayoutInScreen.isAccessible = true
        mLayoutInScreen.set(mPopupWindow, needFullScreen)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



fun getDisplayMetrics(context: Context): DisplayMetrics {
    val metric = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
    return metric
}