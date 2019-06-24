package example.ken.galleymukey.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.source.dto.NewGoodsSellDto

@Dao
interface NewGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBean(bean : NewGoodsSellDto)


    @Query("select goodsName,coverImg,hotSell from  GoodsListDto inner join NewGoodsSellDto where :hashTag like '%'|| keyword || '%' and GoodsListDto.id == NewGoodsSellDto.keyId limit 10")
    suspend fun findGoodsByHastTag(hashTag:String) : List<NewGoodsBean>

    @Query("select goodsName,coverImg,hotSell from  GoodsListDto  inner join NewGoodsSellDto where GoodsListDto.id == NewGoodsSellDto.keyId limit 10")
    suspend fun findAllGoods() : List<NewGoodsBean>


}