package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * com.example.database.source.dto
 * cuvsu
 * 2019/6/1
 **/
@Entity(tableName = "CateDto")
class CateDto {

    @PrimaryKey(autoGenerate = true)
    var id = 0
    var keyword = ""
    var img = ""
}