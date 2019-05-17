package example.ken.galleymukey.source

import androidx.room.Database
import androidx.room.RoomDatabase
import example.ken.galleymukey.source.dao.UserInfoDao
import example.ken.galleymukey.source.dto.UserInfoDto


/**
 * example.ken.galleymukey.source.dto
 *
 * 2019/5/17
 **/
@Database(entities = arrayOf(UserInfoDto::class),version = 1)
abstract class AppDataBaseCreator : RoomDatabase() {

    abstract fun getUserInfoDto() : UserInfoDao

}