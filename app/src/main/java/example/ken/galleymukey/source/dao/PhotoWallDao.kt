package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.PhotoWallDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/5/18
 **/
@Dao
interface PhotoWallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoBean(bean : PhotoWallDto)


    @Query("select * from PhotoWallDto")
    fun getPhotoBean() : LiveData<List<PhotoWallDto>>


}