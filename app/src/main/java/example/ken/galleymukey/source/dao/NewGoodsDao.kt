package example.ken.galleymukey.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.source.dto.NewGoodsDto

@Dao
interface NewGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBean(bean : NewGoodsDto)


    @Query("select goodsName,logo,hotSell from  NewGoodsDto where :hashTag like '%'|| keywords || '%'")
    suspend fun findGoodsByHastTag(hashTag:String) : List<NewGoodsBean>

    @Query("select goodsName,logo,hotSell from  NewGoodsDto")
    suspend fun findAllGoods() : List<NewGoodsBean>
}