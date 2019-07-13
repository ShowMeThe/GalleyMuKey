package com.example.database.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.source.dto.HashTagDto

/**
 * com.example.database.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface HashTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHash(dto : HashTagDto)

    @Query("select * from HashTagDto")
    fun findAll() : List<HashTagDto>

}