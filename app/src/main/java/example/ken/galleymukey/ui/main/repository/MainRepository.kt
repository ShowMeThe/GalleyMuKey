package example.ken.galleymukey.ui.main.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.CateTagBean
import example.ken.galleymukey.bean.HashTagBean
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.CommentDto
import example.ken.galleymukey.source.dto.GoodsListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.rden.RDEN

/**
 * example.ken.galleymukey.ui.main.repository
 * cuvsu
 * 2019/5/18
 **/
class MainRepository : BaseRepository() {

    val photoDao = DataSourceBuilder.getPhotoWall()
    val imgUrlDao = DataSourceBuilder.getImageDao()
    val hotDto = DataSourceBuilder.getHotWall()
    val goodDao = DataSourceBuilder.getGoodsList()
    val hashTagDao = DataSourceBuilder.getHashTag()
    val cateDao = DataSourceBuilder.getCateDao()
    val infoDao = DataSourceBuilder.getUserDao()
    val comDao = DataSourceBuilder.getCommentDao()


    fun getCommentById(id:Int,result: MutableLiveData<List<CommentDto>>){
        comDao.findCommentById(id).observe(owner!!, Observer {
            it?.apply {
                result.value = this
            }
        })
    }


    fun addComment(id:Int,commemnt:String){
       comDao.addComment(CommentDto(id,commemnt))
        showToast("Add Successfully")
    }

    fun getCustomBg(result :MutableLiveData<String>){
        infoDao.getCustom(RDEN.get(RdenConstant.account,"")).observe(owner!!, Observer {
            it?.apply {
                result.value = it
            }
        })
    }


    fun setCustomBg(newBg : String){
        infoDao.updateCustom(RDEN.get(RdenConstant.account,""),newBg)
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
                bean.value = list


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
                bean.value = list
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
                bean.value = list
            }
        })
    }

    fun setLike(id :Int,like: Boolean){
        GlobalScope.launch (Dispatchers.Main){
            photoDao.setIdLike(id,like)
        }
    }

    fun getGoodsList(first:Int,bean: MutableLiveData<List<GoodsListDto>>){

        goodDao.findFStAll(first).observe(owner!!, Observer {
            it?.apply {
                bean.value = this
            }
        })
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