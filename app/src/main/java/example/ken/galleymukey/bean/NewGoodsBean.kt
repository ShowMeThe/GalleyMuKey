package example.ken.galleymukey.bean

import androidx.room.ColumnInfo
import androidx.room.Ignore

class NewGoodsBean {

    var goodsName = ""
    var hotSell = 0.0f
    @ColumnInfo(name = "coverImg")
    var logo = ""

    @Ignore
    var isRotate = false
}
