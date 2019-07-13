package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.source.dto.GoodsListDto

/**
 * com.example.database.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface GoodsListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(dto : GoodsListDto)


    @Query("select * from GoodsListDto where firstType = :first ")
    fun findFStAll(first:Int) : LiveData<List<GoodsListDto>>

    @Query("delete  from GoodsListDto")
    fun delete()

}