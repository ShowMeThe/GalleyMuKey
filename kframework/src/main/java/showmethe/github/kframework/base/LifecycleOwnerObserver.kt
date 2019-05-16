package showmethe.github.kframework.base

import androidx.lifecycle.LifecycleOwner

/**
 * showmethe.github.kframework.base
 *
 * 2019/4/4
 **/
interface LifecycleOwnerObserver  {
    fun update(owner: LifecycleOwner) //更新方法
}