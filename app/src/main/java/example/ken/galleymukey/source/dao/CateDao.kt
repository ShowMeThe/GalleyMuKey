package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import example.ken.galleymukey.source.dto.CateDto

/**
 * example.ken.galleymukey.source.dao
 * cuvsu
 * 2019/6/1
 **/
@Dao
interface CateDao {


    @Insert
    fun addCate(bean : CateDto)

    @Query("select * from catedto where :keywords like '%'|| keyword || '%'")
    fun findCate(keywords:String) : LiveData<List<CateDto>>

    @Query("select * from catedto")
    fun findAll() : LiveData<List<CateDto>>

}