package showmethe.github.kframework.util.rden

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * com.example.ken.kmvvm
 *
 *
 * 2019/5/13
 */
@Entity(tableName = "RoomBean")
class RoomBean {


    @PrimaryKey
    lateinit var storeKey: String
    var stringValue: String? = null
    var booleanValue: Boolean? = null
    var longValue: Long? = null
    var integerValue: Int? = null
    var bytesValue: ByteArray? = null
}
