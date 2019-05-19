package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import example.ken.galleymukey.bean.HotWallBean
import example.ken.galleymukey.source.dto.HotWallDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/5/19
 **/
@Dao
interface HotWallDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHotBean(bean : HotWallDto)

    @Query("select * from HotWallDto")
    fun getHotBeanList() : LiveData<List<HotWallDto>>

    @Query("delete  from HotWallDto")
    fun deleteAll()

}