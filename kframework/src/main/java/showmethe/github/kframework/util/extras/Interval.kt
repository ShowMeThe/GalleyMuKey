package showmethe.github.kframework.util.extras

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 *  动作静止后一段时间执行对应内容  过滤频繁触发 如 vp切换页面时候触发网络请求
 */
class Interval(var delay:Long,var filter:Long = 250L) {

    private var handler = Handler(
        WeakReference(Handler.Callback {
            when(it.what){
                  step -> {
                      onMethod?.invoke()
                  }
            }
        true
    }).get())

    private var step = 0
    private var lastStep = 0
    private var lastTime = 0L
    private var onMethod : (()->Unit)? = null

    fun start(onMethod : (()->Unit)){
        step ++
       val last  = System.currentTimeMillis() - lastTime
        if(last>filter){
            this.onMethod = onMethod
            lastTime = last
            handler.sendMessageDelayed(Message.obtain(handler,step),delay)
        }
        if(lastStep > step){
            handler.removeMessages(lastStep)
        }
        lastStep = step
    }

    fun clean(){
        handler.removeCallbacksAndMessages( null)
    }

}