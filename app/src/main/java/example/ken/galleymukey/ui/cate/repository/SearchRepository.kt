package example.ken.galleymukey.ui.cate.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.HotWallDto
import showmethe.github.kframework.base.BaseRepository

/**
 * example.ken.galleymukey.ui.cate.repository
 * cuvsu
 * 2019/6/8
 **/
class SearchRepository : BaseRepository() {

    val search = DataSourceBuilder.getPhotoWall()

    fun searchUser(name:String,result:MutableLiveData<ArrayList<PhotoWallBean>>){
        search.findUser(name).observe(owner!!, Observer {
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
                result.value = list
            }
        })
    }

}