package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.database.source.dto.PhotoWallDto

/**
 * com.example.database.source.dao
 * cuvsu
 * 2019/5/18
 **/
@Dao
interface PhotoWallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoBean(bean : PhotoWallDto)


    @Query("select * from PhotoWallDto order by id ASC")
    fun getPhotoBean() : LiveData<List<PhotoWallDto>>

    @Query("delete  from PhotoWallDto")
    fun delete()

    @Query("update  PhotoWallDto set `like` = :like where id = :id")
    fun setIdLike(id:Int,like: Boolean)


    @Query("select  *  from PhotoWallDto where id = :id")
    fun findBean(id:Int) : PhotoWallDto


    @Query("select  *  from PhotoWallDto where username   like '%'|| :name  || '%' ")
    fun findUser(name:String) : LiveData<List<PhotoWallDto>>


    @Query("select  * from PhotoWallDto where `like` = :value ")
    fun findAllLike(value: Boolean) : LiveData<List<PhotoWallDto>>

    @Query("update   PhotoWallDto set count = count+1 where id = :id ")
    fun addCountNumber(id: Int)

}