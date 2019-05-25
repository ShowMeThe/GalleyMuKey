package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import example.ken.galleymukey.source.dto.UserInfoDto
import showmethe.github.kframework.util.rden.RoomBean

/**
 * example.ken.galleymukey.source
 *
 * 2019/5/17
 **/
@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(bean : UserInfoDto)

    @Query("select * from UserInfoDto where account  = (:account) and password  = (:password)")
    fun query(account :String?,password : String?) : LiveData<UserInfoDto>

    @Query("select * from UserInfoDto where account  = (:account)")
    fun queryAccount(account :String?) : LiveData<UserInfoDto>

    @Update
    fun updateUserInfo(dto : UserInfoDto) : Int

    @Query("Update  UserInfoDto set password=:password where  account = :account  ")
    fun updatePassword(account:String,password: String) : Int



}