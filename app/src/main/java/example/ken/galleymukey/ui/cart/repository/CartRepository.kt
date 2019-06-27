package example.ken.galleymukey.ui.cart.repository

import android.util.Log
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


    fun addCartBean(bean:CartListDto){
        GlobalScope.launch(Dispatchers.IO) {
            goodsDao.addCartBean(bean)
        }
    }


}