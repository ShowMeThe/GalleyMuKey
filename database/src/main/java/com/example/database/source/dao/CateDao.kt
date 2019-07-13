package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.source.dto.CateDto

/**
 * com.example.database.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface CateDao {


    @Insert
    fun addCate(bean : CateDto)

    @Query("select * from catedto where :keywords like '%'|| keyword || '%'")
    fun findCate(keywords:String) : LiveData<List<CateDto>>

    @Query("select * from catedto")
    fun findAll() : LiveData<List<CateDto>>

}