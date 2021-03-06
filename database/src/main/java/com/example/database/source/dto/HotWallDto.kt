package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * com.example.database.source.dto
 * cuvsu
 * 2019/5/19
 */
@Entity(tableName = "HotWallDto")
class HotWallDto {

    @PrimaryKey(autoGenerate = true)
    var id = 0
    var imageTop: String? = null
    var imageBottom: String? = null
    var type = 0
}
