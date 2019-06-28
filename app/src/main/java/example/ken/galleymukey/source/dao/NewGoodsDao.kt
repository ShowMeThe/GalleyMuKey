package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.source.dto.CartListDto
import example.ken.galleymukey.source.dto.NewGoodsSellDto

@Dao
interface NewGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBean(bean : NewGoodsSellDto)


    @Query("select goodsName,coverImg,hotSell from  GoodsListDto inner join NewGoodsSellDto where :hashTag like '%'|| keyword || '%' and GoodsListDto.id == NewGoodsSellDto.keyId limit :pagerNumber,10 ")
    suspend fun findGoodsByHastTag(hashTag:String,pagerNumber:Int) : List<NewGoodsBean>

    @Query("select goodsName,coverImg,hotSell from  GoodsListDto  inner join NewGoodsSellDto where GoodsListDto.id == NewGoodsSellDto.keyId  limit :pagerNumber,10 ")
    suspend fun findAllGoods(pagerNumber:Int) : List<NewGoodsBean>


    @Query("select cardId,goodsId,count,goodsName,price,coverImg,goodsDes from GoodsListDto inner join CartListDto where GoodsListDto.id == CartListDto.goodsId")
    fun findCartList() : LiveData<List<CartListBean>>

    @Query("select * from CartListDto where goodsId == :goodsId")
    suspend fun findCart(goodsId :Int ) : List<CartListDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCartBean(bean : CartListDto)

    @Query("delete from CartListDto where cardId == :id")
    fun deleteCartById(id:Int)

}