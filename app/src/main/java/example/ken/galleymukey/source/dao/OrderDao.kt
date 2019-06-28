package example.ken.galleymukey.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.source.dto.OrderDto

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrderByBeans(list: List<OrderDto>)

    @Query("select coverImg, orderId,orderGoodsId ,totalPrice,totalCount,goodsName from GoodsListDto  inner join OrderDto where GoodsListDto.id == OrderDto.orderGoodsId limit :pagerNumber,10")
     suspend fun qureyOrderList(pagerNumber :Int) : List<OrderListBean>

}