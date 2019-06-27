package example.ken.galleymukey.ui.cart.repository

import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.CartListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository

class CartRepository : BaseRepository() {

    val goodsDao = DataSourceBuilder.getNewGoodsDao()



    fun findCartList(pager:Int, reuslt : MutableLiveData<List<CartListBean>>){
        GlobalScope.launch (Dispatchers.IO){
           val list =   goodsDao.findCartList((pager-1)*10)
            reuslt.postValue(list)
        }
    }


    fun addCartBean(id:Int,boolean: MutableLiveData<Boolean>){
        GlobalScope.launch(Dispatchers.IO) {
            val result = goodsDao.findCart(id)
            if (result.isNotEmpty()){
                val bean = result[0]
                bean.count += 1
                goodsDao.addCartBean(bean)
            }else{
                val bean = CartListDto()
                bean.count = 1
                bean.goodsId = id
                goodsDao.addCartBean(bean)
            }
            boolean.postValue(true)
        }
    }


}