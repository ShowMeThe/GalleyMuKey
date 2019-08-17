package showmethe.github.kframework.util.rden

import androidx.room.*

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
    fun get(key :String ) : RoomBean?


    @Query("delete from RoomBean  where storeKey = (:key)")
    fun delete(key :String )
}
