package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.source.converters.ArraysConverters

/**
 * com.example.database.source.dto
 * cuvsu
 * 2019/5/17
 **/

@Entity(tableName = "ImageUrlDto")
@TypeConverters(ArraysConverters::class)
class ImageUrlDto {

    @PrimaryKey
    lateinit var key : String
    var arrarys : ArrayList<String> ? = null

}