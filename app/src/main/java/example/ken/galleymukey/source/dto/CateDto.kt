package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * example.ken.galleymukey.source.dto
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