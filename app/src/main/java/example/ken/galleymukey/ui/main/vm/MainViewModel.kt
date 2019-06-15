package example.ken.galleymukey.ui.main.vm

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.CateTagBean
import example.ken.galleymukey.bean.HashTagBean
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.source.dto.GoodsListDto
import example.ken.galleymukey.source.dto.HashTagDto
import example.ken.galleymukey.source.dto.PhotoWallDto
import example.ken.galleymukey.ui.cate.repository.SearchRepository
import example.ken.galleymukey.ui.main.repository.MainRepository
import showmethe.github.kframework.base.BaseViewModel

/**
 * example.ken.galleymukey.ui.main.vm
 * cuvsu
 * 2019/5/18
 **/
class MainViewModel(application: Application) : BaseViewModel(application) {

    val repository = MainRepository()
    val searchRepository = SearchRepository()

    val users = MutableLiveData<ArrayList<PhotoWallBean>>()
    val searchContent = MutableLiveData<String>()

    var cateChildManager : FragmentManager? = null
    var catePopBack = MutableLiveData<Boolean>()
    val bean  = MutableLiveData<ArrayList<PhotoWallBean>>()
    val hotBean = MutableLiveData<ArrayList<HotWallBean>>()

    val boolean = MutableLiveData<Boolean>()
    val hashTag = MutableLiveData<ArrayList<HashTagBean>>()
    val keyword = MutableLiveData<String>()
    val cate = MutableLiveData<ArrayList<CateTagBean>>()

    val customBg = MutableLiveData<String>()


    override fun onViewModelCreated(owner: LifecycleOwner) {
        repository.owner = owner
    }

    fun searchUser(name:String){
        searchRepository.searchUser(name,users)
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


    fun getGoodsList(first:Int,goodsBean : MutableLiveData<List<GoodsListDto>>){
        repository.getGoodsList(first,goodsBean)
    }

    fun getHashTag(){
        repository.getHashTag(hashTag)
    }

    fun getCate(keyword: String){
        repository.getCate(keyword,cate)
    }

    fun getCustomBg(){
        repository.getCustomBg(customBg)
    }

    fun setCustomBg(newBg : String){
        repository.setCustomBg(newBg)
    }

    fun findAllLike(){
        repository.findLikeAll(bean)
    }

}