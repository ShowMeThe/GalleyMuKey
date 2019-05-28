package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * example.ken.galleymukey.source.dto
 * ken
 * 2019/5/17
 **/
@Entity(tableName = "UserInfoDto")
class UserInfoDto {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var account : String? = ""
    var password : String? = ""
    var phone :String =""
    var desContent : String = ""
    var avatar : String=""
    var email : String=""
    var birthday : String=""
}