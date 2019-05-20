package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import example.ken.galleymukey.source.converters.ArraysConverters

/**
 * example.ken.galleymukey.source.dto
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

}