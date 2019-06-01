package example.ken.galleymukey.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.source.dto.HashTagDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface HashTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHash(dto : HashTagDto)

    @Query("select * from HashTagDto")
    fun findAll() : List<HashTagDto>

}