package showmethe.github.kframework.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import showmethe.github.kframework.parallaxbacklayout.ParallaxHelper
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.http.RetroHttp
import showmethe.github.kframework.util.rden.RDEN
import java.lang.ref.WeakReference

/**
 * showmethe.github.kframework.base
 *
 * 2019/1/10
 **/
open class BaseApplication : Application() {

    companion object {
       @SuppressLint("StaticFieldLeak")
       lateinit var context : Context
        var ctx: WeakReference<RxAppCompatActivity>? = null
    }





    override fun onCreate() {
        super.onCreate()
        context = this

        Thread {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND)
            TGlide.init(this)
            RetroHttp.get()
            RDEN.build(this)
        }.start()
        registerActivityLifecycleCallbacks(ParallaxHelper.instance)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {
                if(activity is RxAppCompatActivity){
                    ctx = WeakReference(activity)
                }
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                ctx = null
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }
        })
    }

    override fun onTrimMemory(level: Int) {
        if(ctx!=null){
            ctx = null;
        }
        super.onTrimMemory(level)
    }

}