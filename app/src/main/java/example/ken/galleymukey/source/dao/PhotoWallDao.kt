package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("delete  from PhotoWallDto")
    fun delete()

    @Query("update  PhotoWallDto set `like` = :like where id = :id")
    fun setIdLike(id:Int,like: Boolean)


    @Query("select  *  from PhotoWallDto where id = :id")
    fun findBean(id:Int) : PhotoWallDto
}