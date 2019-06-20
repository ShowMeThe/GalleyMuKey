package example.ken.galleymukey.bean

import androidx.room.Ignore

class NewGoodsBean {

    var goodsName = ""
    var hotSell = 0.0f
    var logo = ""

    @Ignore
    var isRotate = false
}
