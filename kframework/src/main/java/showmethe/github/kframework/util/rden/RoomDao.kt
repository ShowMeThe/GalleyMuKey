package showmethe.github.kframework.util.rden

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * com.example.ken.kmvvm
 *
 *
 * 2019/5/13
 */
@Dao
interface RoomDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(bean : RoomBean)

    @Query("select * from RoomBean where storeKey  = (:key)")
    fun get(key :String ) : RoomBean

}
