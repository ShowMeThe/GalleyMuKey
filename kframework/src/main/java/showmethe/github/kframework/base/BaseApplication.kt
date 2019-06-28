package showmethe.github.kframework.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.http.RetroHttp
import showmethe.github.kframework.parallaxbacklayout.ParallaxBackHelper
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.system.crash.CrashHandler
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
        var ctx: WeakReference<AppCompatActivity>? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        GlobalScope.launch(Dispatchers.IO) {
            TGlide.init(this@BaseApplication)
            RetroHttp.get()
            RDEN.build(this@BaseApplication)
        }
        registerActivityLifecycleCallbacks(ParallaxBackHelper.get())
        registerActivityLifecycleCallbacks(CrashHandler.get(this))
        registerActivityLifecycleCallbacks(object :SimpleLifecyclerCallbacks(){
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if(activity is AppCompatActivity){
                    ctx = WeakReference(activity)
                }
            }
            override fun onActivityResumed(activity: Activity?) {
                if(activity is AppCompatActivity){
                    ctx = WeakReference(activity)
                }
            }
            override fun onActivityDestroyed(activity: Activity?) {

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