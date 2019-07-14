package com.example.router.share

import androidx.fragment.app.FragmentManager

class Share : ShareObservable {


    companion object{
        private var instant : Share? = null
        fun get() : Share{
            if(instant == null){
                instant = Share()
            }
            return instant!!
        }
    }

    private var notifyFragmentManager:((fragmentManager: FragmentManager)->Unit)?=null
    fun onNotifyFragmentManager(notifyFragmentManager:((fragmentManager: FragmentManager)->Unit)){
        this.notifyFragmentManager = notifyFragmentManager
    }
    override fun updateFragmentManager(fragmentManager: FragmentManager) {
        notifyFragmentManager?.invoke(fragmentManager)
    }


    private var popBack:((bool:Boolean)->Unit)? = null

    fun onPopBack (popBack:((bool:Boolean)->Unit)){
        this.popBack = popBack
    }

    override fun popBack(boolean: Boolean) {
        popBack?.invoke(boolean)
    }

}