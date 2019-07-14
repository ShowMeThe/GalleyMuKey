package com.example.cate.cate.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.database.bean.CateTagBean
import com.example.database.bean.HashTagBean
import com.example.database.bean.NewGoodsBean
import com.example.database.source.DataSourceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository

class CateRepository : BaseRepository() {

    val newDao  = DataSourceBuilder.getNewGoodsDao()
    val hashTagDao = DataSourceBuilder.getHashTag()
    val cateDao = DataSourceBuilder.getCateDao()



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