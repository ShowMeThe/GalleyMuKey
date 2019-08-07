package com.example.ken.kmvvm

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference



class Interval(private var delay:Long,private var filter:Long = 250L) {

    private var handler : WeakReference<Handler>? = null
    init {
        handler = WeakReference(Handler(
            WeakReference(Handler.Callback {
                when(it.what){
                    step -> {
                        onMethod?.invoke()
                    }
                }
                true
            }).get()))
    }

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
            handler?.get()?.sendMessageDelayed(Message.obtain(handler?.get(),step),delay)
        }
        if(lastStep > step){
            handler?.get()?.removeMessages(lastStep)
        }
        lastStep = step
    }


    fun clean(){
        handler?.get()?.removeCallbacksAndMessages( null)
        if(handler != null){
            handler = null
        }
    }

}