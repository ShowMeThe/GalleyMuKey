package showmethe.github.kframework.util.system

import android.util.Log

/**
 * showmethe.github.kframework.util.system
 * cuvsu
 * 2019/4/2
 **/
object LogUtil {

    val isDebug = true;

    fun e(tag:String,obj : Any){
        if(isDebug)
        Log.e(tag,"${obj}")
    }

    fun i(tag:String,obj : Any){
        if(isDebug)
            Log.i(tag,"${obj}")
    }


    fun d(tag:String,obj : Any){
        if(isDebug)
            Log.d(tag,"${obj}")
    }

    fun v(tag:String,obj : Any){
        if(isDebug)
            Log.v(tag,"${obj}")
    }

}