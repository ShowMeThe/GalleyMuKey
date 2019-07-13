package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.source.converters.ArraysConverters

/**
 * com.example.database.source.dto
 * cuvsu
 * 2019/5/18
 **/
@Entity(tableName = "PhotoWallDto")
@TypeConverters(ArraysConverters::class)
class PhotoWallDto {

    @PrimaryKey
    var id: Int = 0
    var imageTop : ArrayList<String> ? = null
    var avatar : String? = null
    var username : String? = null
    var like  : Boolean = false
    var count  : Int = 0
}