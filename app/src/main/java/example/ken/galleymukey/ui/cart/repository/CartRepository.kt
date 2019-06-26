package example.ken.galleymukey.ui.cart.repository

import android.util.Log
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.CartListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository

class CartRepository : BaseRepository() {

    val goodsDao = DataSourceBuilder.getNewGoodsDao()



    fun findCartList(){
        GlobalScope.launch (Dispatchers.IO){
          val list =   goodsDao.findCartList()
            Log.e("2222222222","${list.size}")
        }
    }


    fun addCartBean(bean:CartListDto){
        GlobalScope.launch(Dispatchers.IO) {
            goodsDao.addCartBean(bean)
        }
    }


}