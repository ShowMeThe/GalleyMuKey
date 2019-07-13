package com.example.router.router

import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter


class RouteServiceManager {

    companion object{

        fun <T : IProvider> provide(clz : Class<T>) : T?{

            var iProvider:IProvider? = null
            try {
                iProvider = ARouter.getInstance().navigation(clz) as IProvider
            }catch (e : Exception){
                e.printStackTrace()
            }
            return iProvider as T?
        }
    }
}