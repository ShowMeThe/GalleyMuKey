package example.ken.galleymukey.ui.main.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.source.dto.PhotoWallDto
import example.ken.galleymukey.ui.main.repository.MainRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.main.vm
 * cuvsu
 * 2019/5/18
 **/
class MainViewModel(application: Application) : BaseViewModel(application) {

    val repository = MainRepository()
    val bean  = MutableLiveData<ArrayList<PhotoWallBean>>()
    val hotBean = MutableLiveData<ArrayList<HotWallBean>>()
    val boolean = MutableLiveData<Boolean>()

    override fun onViewModelCreated(owner: LifecycleOwner) {


    }

    override fun notifyOwner(owner: LifecycleOwner) {
        repository.update(owner)
    }


    fun getHomePhoto(){
        repository.getHomePhoto(bean)
    }

    fun getHotWall(){
        repository.getHotWall(hotBean)
    }

    fun setLike(id :Int,like: Boolean){
        repository.setLike(id,like)
    }
}