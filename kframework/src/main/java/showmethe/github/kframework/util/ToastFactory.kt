package showmethe.github.kframework.util

import android.app.Activity
import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


import showmethe.github.kframework.R
import showmethe.github.kframework.base.BaseApplication

import android.view.FrameMetrics.ANIMATION_DURATION

/**
 * PackageName: example.ken.com.library.utils
 * Author : jiaqi Ye
 * Date : 2018/11/12
 * Time : 9:14
 */
object ToastFactory {

    private var toast: Toast? = null

    private var imgToast: Toast? = null

    private val mHandler = Handler(Looper.getMainLooper())


    fun createToast(message: Any) {
        BaseApplication.ctx?.get()?.runOnUiThread {
            if (isNotificationEnabled(BaseApplication.context)) {
                createNormalToast(message)
            } else {
                createViewToast(message)
            }
        }
    }


    private fun createViewToast(message: Any) {
        if (BaseApplication.ctx == null) {
            return
        }
        val container = BaseApplication.ctx!!.get()!!
                .findViewById<ViewGroup>(android.R.id.content)
        val mView = View.inflate(BaseApplication.context, R.layout.view_toast_draw, null)

        container.addView(mView)

        val title = mView.findViewById<TextView>(R.id.text)
        title.text = message.toString()

        // 显示动画
        val mFadeInAnimation = AlphaAnimation(0.0f, 1.0f)
        // 消失动画
        val mFadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
        mFadeOutAnimation.duration = 200
        mFadeOutAnimation
                .setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {

                    }
                    override fun onAnimationEnd(animation: Animation) {
                        // 隐藏布局，不使用remove方法为防止多次创建多个布局
                        mView.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })

        mView.visibility = View.VISIBLE
        mFadeInAnimation.duration = 300

        mView.startAnimation(mFadeInAnimation)
        mHandler.postDelayed({ mView.startAnimation(mFadeOutAnimation) }, 1500)
    }


    private fun createNormalToast(message: Any) {
        val title: TextView
        val layout: View
        if (toast == null) {
            layout = View.inflate(BaseApplication.context, R.layout.view_toast_draw, null)
        } else {
            toast?.cancel()
            layout = toast!!.view
        }
        title = layout.findViewById(R.id.text)
        title.text = message.toString()
        toast = Toast.makeText(BaseApplication.context, "", Toast.LENGTH_SHORT)
        toast?.apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = Toast.LENGTH_SHORT
            setText(message.toString())
            view = layout
            show()
        }
    }


    fun createImgToast(isTrue: Boolean, message: Any) {
        val title: TextView
        val imageView: ImageView
        val layout: View
        if (imgToast == null) {
            layout = View.inflate(BaseApplication.context, R.layout.view_img_toast, null)
        } else {
            imgToast?.cancel()
            layout = imgToast!!.view
        }
        title = layout.findViewById(R.id.text)
        imageView = layout.findViewById(R.id.image)
        if (isTrue) {
            title.setTextColor(Color.parseColor("#3F75FF"))

        } else {
            title.setTextColor(Color.parseColor("#333333"))

        }
        title.text = message.toString()
        imgToast = Toast.makeText(BaseApplication.context, "", Toast.LENGTH_SHORT)
        imgToast?.apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = Toast.LENGTH_SHORT
            setText(message.toString())
            view = layout
            show()
        }

    }


    /**
     * 检查通知栏权限有没有开启
     *
     */
    fun isNotificationEnabled(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).areNotificationsEnabled()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val appInfo = context.applicationInfo
            val pkg = context.applicationContext.packageName
            val uid = appInfo.uid

            try {
                val appOpsClass = Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String::class.java)
                val opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION")
                val value = opPostNotificationValue.get(Int::class.java) as Int
                return checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) as Int == 0
            } catch (ignored: Exception) {
                return true
            }

        } else {
            return true
        }
    }
}
