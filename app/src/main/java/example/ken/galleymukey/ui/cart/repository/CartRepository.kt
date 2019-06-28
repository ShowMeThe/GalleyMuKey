package example.ken.galleymukey.ui.cart.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.source.DataSourceBuilder
import example.ken.galleymukey.source.dto.CartListDto
import example.ken.galleymukey.source.dto.OrderDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseRepository
import showmethe.github.kframework.util.rden.RDEN
import kotlin.math.log

class CartRepository : BaseRepository() {

    val goodsDao = DataSourceBuilder.getNewGoodsDao()
    val orderDao = DataSourceBuilder.getOrderDao()
    val userInfoDto = DataSourceBuilder.getUserDao()

    fun findCartList( reuslt : MutableLiveData<List<CartListBean>>){
        goodsDao.findCartList().observe(owner!!, Observer {
            it?.apply {
                reuslt.value = this
            }
        })
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

    fun addOrder(listBean: List<OrderDto>,result:MutableLiveData<Boolean>){
        GlobalScope.launch (Dispatchers.IO){
            orderDao.addOrderByBeans(listBean)
            for(bean in listBean){
                goodsDao.deleteCartById(bean.cardId)
            }
            var price = 0.0
            listBean.forEach {
                price  +=  it.totalPrice
            }
            val d  = userInfoDto.updateWallet(RDEN.get(RdenConstant.account,""),price)
            Log.e("2222222","$d")
            showToast("Check Out Successfully")
            result.postValue(true)
        }
    }

    fun qureyOrderList(pagerNumber :Int ,data : MutableLiveData<List<OrderListBean>>){
        GlobalScope.launch(Dispatchers.IO){
            val result = orderDao.qureyOrderList((pagerNumber - 1)*10)
            data.postValue(result)
        }
    }

}