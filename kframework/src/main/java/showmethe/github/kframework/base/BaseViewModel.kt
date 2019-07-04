package showmethe.github.kframework.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.jeremyliao.liveeventbus.LiveEventBus
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.util.ToastFactory

/**
 * showmethe.github.kframework.base
 *
 * 2019/1/10
 **/
abstract class BaseViewModel    (application: Application) : AndroidViewModel(application),DefaultLifecycleObserver{


    lateinit var owner: LifecycleOwner
    private var onInit : ((owner: LifecycleOwner)->Unit )? = null

    override fun onCreate(owner: LifecycleOwner) {
        this.owner = owner

        onViewModelCreated(owner)
        onInit?.invoke(owner)

    }

    abstract fun onViewModelCreated(owner: LifecycleOwner)


    fun LifecyclerCreated(onInit : (owner: LifecycleOwner)->Unit){
        this.onInit = onInit
    }



    fun showToast(message : String){
        ToastFactory.createToast(message)
    }

    override fun onCleared() {
        super.onCleared()

    }

}

