package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = GoodsListDto::class,parentColumns = ["id"],childColumns = ["goodsId"],onDelete = ForeignKey.CASCADE)],
    indices =[Index(value = ["goodsId"],unique = true)])
class CartListDto {

    @PrimaryKey(autoGenerate = true)
    var cardId = 0
    var count = 0
    var goodsId = 0



}
