package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import example.ken.galleymukey.source.converters.ArraysConverters

/**
 * example.ken.galleymukey.source.dto
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