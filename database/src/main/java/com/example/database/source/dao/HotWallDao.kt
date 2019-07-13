package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.database.source.dto.HotWallDto

/**
 * com.example.database.source.dao
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