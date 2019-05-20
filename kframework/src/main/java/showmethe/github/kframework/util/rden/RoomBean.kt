package showmethe.github.kframework.util.rden

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * com.example.ken.kmvvm
 *
 *
 * 2019/5/13
 */
@Entity(tableName = "RoomBean")
@TypeConverters(Converters::class)
class RoomBean {


    @PrimaryKey
    lateinit var storeKey: String
    var stringValue: String? = null
    var booleanValue: Boolean? = null
    var longValue: Long? = null
    var integerValue: Int? = null
    var bytesValue: ByteArray? = null
    var listValue: ArrayList<*>? = null
}
