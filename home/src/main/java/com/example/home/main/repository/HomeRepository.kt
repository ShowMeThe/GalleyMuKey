package com.example.home.main.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.database.bean.*
import com.example.router.constant.RdenConstant
import com.example.database.source.DataSourceBuilder
import com.example.database.source.dto.CommentDto
import com.example.database.source.dto.GoodsListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.rden.RDEN

/**
 * com.example.home.main.repository
 * cuvsu
 * 2019/5/18
 **/
class HomeRepository : BaseRepository() {

    val photoDao = DataSourceBuilder.getPhotoWall()
    val hotDto = DataSourceBuilder.getHotWall()

    val hashTagDao = DataSourceBuilder.getHashTag()
    val cateDao = DataSourceBuilder.getCateDao()

    val comDao = DataSourceBuilder.getCommentDao()
    val newDao  = DataSourceBuilder.getNewGoodsDao()


    fun findGoodsByHashTag(tag:String, pagerNumber : Int ,data : MutableLiveData<List<NewGoodsBean>>){
        GlobalScope.launch(Dispatchers.IO) {
            val list  = if(tag.isEmpty()){
                newDao.findAllGoods((pagerNumber-1)*10)
            }else{
                newDao.findGoodsByHastTag(tag,(pagerNumber-1)*10)
            }
            data.postValue(list)
        }
    }


    fun getCommentById(id:Int,result: MutableLiveData<List<CommentDto>>){
        GlobalScope.launch(Dispatchers.IO)  {
            val list =  comDao.findCommentById(id)
            list.apply {
                result.postValue(this)
            }
        }


    }


    fun addComment(id:Int,commemnt:String){
        comDao.addComment(CommentDto(id,commemnt))
        showToast("Add Successfully")
    }




    fun getHomePhoto(bean : MutableLiveData<ArrayList<PhotoWallBean>>){
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
                    bean.count = dto.count
                    list.add(bean)
                }
                bean.postValue(list)
            }
        })
    }

    fun findLikeAll(bean : MutableLiveData<ArrayList<PhotoWallBean>>){
        photoDao.findAllLike(true).observe(owner!!, Observer {
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
                bean.postValue(list)
            }
        })
    }


    fun getHotWall(bean  : MutableLiveData<ArrayList<HotWallBean>>){
        hotDto.getHotBeanList().observe(owner!!, Observer {
            it?.apply {
                val list = ArrayList<HotWallBean>()
                for(beans in this){
                    val hot = HotWallBean(beans.imageTop,beans.imageBottom,beans.type)
                    list.add(hot)
                }
                bean.postValue(list)
            }
        })
    }

    fun setLike(id :Int,like: Boolean){
        GlobalScope.launch (Dispatchers.Main){
            photoDao.setIdLike(id,like)
        }
    }


    fun getHashTag(beans : MutableLiveData<ArrayList<HashTagBean>>){
        val list = hashTagDao.findAll()
        val beanList = ArrayList<HashTagBean>()
        for(dto in list){
            val bean = HashTagBean(dto.img,dto.keyword)
            beanList.add(bean)
        }
        beans.value = beanList
    }

    fun getCate(keyword:String,cateDto: MutableLiveData<ArrayList<CateTagBean>>){
        if(keyword.isEmpty()){
            cateDao.findAll().observe(owner!!, Observer {
                val list = ArrayList<CateTagBean>()
                it?.apply {
                    for(bean in this){
                        val tagBean = CateTagBean()
                        tagBean.img = bean.img
                        tagBean.keyword = bean.keyword
                        list.add(tagBean)
                    }
                    cateDto.value = list
                }
            })
        }else{
            cateDao.findCate(keyword).observe(owner!!, Observer {
                val list = ArrayList<CateTagBean>()
                it?.apply {
                    for(bean in this){
                        val tagBean = CateTagBean()
                        tagBean.img = bean.img
                        tagBean.keyword = bean.keyword

                        list.add(tagBean)
                    }
                    cateDto.value = list
                }
            })

        }

    }

}