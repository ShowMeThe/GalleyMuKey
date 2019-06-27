package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = GoodsListDto::class,parentColumns = ["id"],childColumns = ["orderGoodsId"],onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["orderId"],unique = true)])
class OrderDto {

    @PrimaryKey(autoGenerate = true)
    var orderId = 0
    var orderGoodsId = 0
    var totalPrice = 0.0
    var totalCount = 0
}