package example.ken.galleymukey.ui.main.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.PhotoWallDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository

/**
 * example.ken.galleymukey.ui.main.repository
 * cuvsu
 * 2019/5/18
 **/
class MainRepository : BaseRepository() {

    val photoDao = DataSourceBuilder.getPhotoWall()
    val imgUrlDao = DataSourceBuilder.getImageDao()
    val hotDto = DataSourceBuilder.getHotWall()

    fun getHomePhoto(bean : MutableLiveData<ArrayList<PhotoWallBean>>){
        showLoading()
        GlobalScope.launch (Dispatchers.Main){
            photoDao.getPhotoBean().observe(owner!!, Observer {
                it?.apply {
                    val list = ArrayList<PhotoWallBean>()
                    for(dto  in this){
                        val bean = PhotoWallBean()
                        bean.id = dto.id
                        bean.imagePaths = dto.imageTop
                        bean.avatar = dto.avatar
                        bean.username = dto.username
                        bean.like = dto.like
                        list.add(bean)
                    }
                    bean.value = list
                    dismissLoading()

                }
            })
        }
    }


    fun getHotWall(bean  : MutableLiveData<ArrayList<HotWallBean>>){
        showLoading()
        GlobalScope.launch (Dispatchers.Main){
            hotDto.getHotBeanList().observe(owner!!, Observer {
                it?.apply {
                    val list = ArrayList<HotWallBean>()
                    for(beans in this){
                        val hot = HotWallBean(beans.imageTop,beans.imageBottom,beans.type)
                        list.add(hot)
                    }
                    bean.value = list
                    dismissLoading()
                }
            })
        }
    }

    fun setLike(id :Int,like: Boolean){
        GlobalScope.launch (Dispatchers.Main){
            photoDao.setIdLike(id,like)
        }
    }


}