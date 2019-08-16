package showmethe.github.kframework.base

import android.app.Activity
import android.content.Context
import java.util.*

/**
 * showmethe.github.kframework.base
 *
 * 2019/1/10
 **/
class AppManager private constructor(){

    companion object {

        internal var stack: Stack<Activity>? = null

        fun get() : AppManager{
            val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                AppManager() }
            return  instance
        }
    }



    fun addActivity(activity: Activity){
        if(stack == null){
            stack = Stack()
        }
        stack?.apply {
            add(activity)
        }
    }

    fun removeActivity(target: Activity){
        stack?.apply {
            stack?.remove(target)
        }
    }


    fun finishTarget(cls : Class<*>){
        stack?.apply {
            for(activity in this){
                if(activity.javaClass == cls){
                    activity.finishAfterTransition()
                    stack?.remove(activity)
                    break
                }
            }
        }
    }

    fun finishAllWithoutTarget(cls : Class<*>){
        stack?.apply {
            for(activity in this){
                if(activity.javaClass != cls){
                    activity.finishAfterTransition()
                    stack?.remove(activity)
                }
            }
        }
    }

    fun finishWithoutParams(cls : Class<*>,cls2 : Class<*>){
        stack?.apply {
            for(activity in this){
                if((activity.javaClass != cls) and (activity.javaClass != cls2)){
                    activity.finishAfterTransition()
                    stack?.remove(activity)
                }
            }
        }
    }



}