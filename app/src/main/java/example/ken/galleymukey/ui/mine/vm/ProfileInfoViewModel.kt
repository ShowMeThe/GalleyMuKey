package example.ken.galleymukey.ui.mine.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.mine.vm
 *
 * 2019/5/25
 **/
class ProfileInfoViewModel(application: Application) : BaseViewModel(application) {

    val switchToReset = MutableLiveData<Boolean>()
    var currentType = true //true info //false reset

    override fun onViewModelCreated(owner: LifecycleOwner) {

    }

    override fun notifyOwner(owner: LifecycleOwner) {
    }
}