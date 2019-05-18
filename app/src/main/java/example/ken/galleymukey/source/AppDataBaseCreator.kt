package example.ken.galleymukey.source

import androidx.room.Database
import androidx.room.RoomDatabase
import example.ken.galleymukey.source.dao.ImageUrlDao
import example.ken.galleymukey.source.dao.PhotoWallDao
import example.ken.galleymukey.source.dao.UserInfoDao
import example.ken.galleymukey.source.dto.ImageUrlDto
import example.ken.galleymukey.source.dto.PhotoWallDto
import example.ken.galleymukey.source.dto.UserInfoDto


/**
 * example.ken.galleymukey.source.dto
 *
 * 2019/5/17
 **/
@Database(entities = arrayOf(UserInfoDto::class,ImageUrlDto::class,PhotoWallDto::class),version = 4)
abstract class AppDataBaseCreator : RoomDatabase() {

    abstract fun getUserInfoDto() : UserInfoDao

    abstract fun getImageUrlDao() : ImageUrlDao

    abstract fun getPhotoDao() : PhotoWallDao

}