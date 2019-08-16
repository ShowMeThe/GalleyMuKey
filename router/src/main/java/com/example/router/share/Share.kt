package com.example.router.share

import androidx.fragment.app.FragmentManager

class Share : ShareObservable {


    companion object{
        private val  instant : Share by lazy { Share() }

        fun get() : Share{
            return instant
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