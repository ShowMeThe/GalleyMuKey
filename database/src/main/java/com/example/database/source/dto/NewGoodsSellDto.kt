package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = GoodsListDto::class,parentColumns = ["id"],childColumns = ["keyId"],onDelete = ForeignKey.CASCADE)],indices = [Index(value = ["keyId"],unique = true)])
class NewGoodsSellDto {

    @PrimaryKey
    var keyId = 0
    var hotSell = 0f


}