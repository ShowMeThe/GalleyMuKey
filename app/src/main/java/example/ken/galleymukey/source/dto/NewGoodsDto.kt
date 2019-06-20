package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "NewGoodsDto")
class NewGoodsDto {


    @PrimaryKey(autoGenerate = true)
    var id = 0
    var hotSell = 0f
    var keywords = ""
    var logo = ""
    var goodsName = ""


}