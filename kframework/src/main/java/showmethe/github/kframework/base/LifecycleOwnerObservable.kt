package showmethe.github.kframework.base

import androidx.lifecycle.LifecycleOwner

/**
 * showmethe.github.kframework.base
 *
 * 2019/4/4
 **/

interface LifecycleOwnerObservable {
     fun notifyOwner(owner: LifecycleOwner) //通知LifecycleOwner变化
}