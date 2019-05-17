package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.UserInfoDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/5/17
 **/
@Dao
interface ImageUrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImages(bean : ImageUrlDto)

    @Query("select * from ImageUrlDto where `key`  = (:key)")
    fun getImages(key: String) : LiveData<ImageUrlDto>

}