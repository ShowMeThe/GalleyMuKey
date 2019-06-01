package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.source.dto.GoodsListDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface GoodsListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(dto : GoodsListDto)


    @Query("select * from GoodsListDto where firstType = :first and secondType = :second")
    fun findFStAll(first:Int,second:Int) : LiveData<List<GoodsListDto>>

    @Query("delete  from GoodsListDto")
    fun delete()

}