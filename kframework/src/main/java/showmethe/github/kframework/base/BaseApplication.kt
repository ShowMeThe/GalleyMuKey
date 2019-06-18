package showmethe.github.kframework.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.http.RetroHttp
import showmethe.github.kframework.parallaxbacklayout.ParallaxBackHelper
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
        GlobalScope.launch {
            TGlide.init(this@BaseApplication)
            RetroHttp.get()
            RDEN.build(this@BaseApplication)
        }
        registerActivityLifecycleCallbacks(ParallaxBackHelper.get())
        registerActivityLifecycleCallbacks(object :SimpleLifecyclerCallbacks(){
            override fun onActivityResumed(activity: Activity?) {
                if(activity is RxAppCompatActivity){
                    ctx = WeakReference(activity)
                }
            }
            override fun onActivityDestroyed(activity: Activity?) {
                ctx = null
            }
        })
    }

    override fun onTrimMemory(level: Int) {
        if(ctx!=null){
            ctx = null
        }
        super.onTrimMemory(level)
    }

}