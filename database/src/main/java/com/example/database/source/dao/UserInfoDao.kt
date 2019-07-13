package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.database.source.dto.UserInfoDto
import showmethe.github.kframework.util.rden.RoomBean

/**
 * com.example.database.source
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

    @Query("select * from UserInfoDto where account  = (:account)")
    fun queryUserInfo(account :String?) : UserInfoDto?

    @Update
    fun updateUserInfo(dto : UserInfoDto) : Int

    @Query("Update  UserInfoDto set password=:password where  account = :account  ")
    fun updatePassword(account:String,password: String) : Int

    @Query("Update  UserInfoDto set customBg =:customBg where  account = :account  ")
    fun updateCustom(account:String,customBg: String) :Int


    @Query("select customBg from UserInfoDto where  account = :account  ")
    fun getCustom(account:String) : LiveData<String>

    @Query("update UserInfoDto set  wallet = wallet - :spend ,totalSpend = totalSpend + :spend where account = :account")
    fun updateWallet(account :String,spend:Double)  : Int

    @Query("update UserInfoDto set  userId = :userId  where account = :account")
    fun updateUserId(userId :String,account :String)  : Int

}