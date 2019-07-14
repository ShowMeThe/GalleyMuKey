package com.example.database.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * com.example.database.source.dto
 * ken
 * 2019/5/17
 **/
@Entity(tableName = "UserInfoDto")
class UserInfoDto {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var userId  = ""
    var account : String? = ""
    var password : String? = ""
    var phone :String =""
    var desContent : String = ""
    var avatar : String=""
    var email : String=""
    var birthday : String=""
    var customBg : String=""
    var wallet  = 10000.0
    var totalSpend = 0.0
}