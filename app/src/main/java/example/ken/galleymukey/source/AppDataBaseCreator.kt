package example.ken.galleymukey.source

import androidx.room.Database
import androidx.room.RoomDatabase
import example.ken.galleymukey.source.dao.*
import example.ken.galleymukey.source.dto.*


/**
 * example.ken.galleymukey.source.dto
 *
 * 2019/5/17
 **/
@Database(entities = arrayOf(UserInfoDto::class,
    ImageUrlDto::class,
    PhotoWallDto::class,
    HotWallDto::class,
    GoodsListDto::class,
    HashTagDto::class,CateDto::class),version = 1)
abstract class AppDataBaseCreator : RoomDatabase() {

    abstract fun getUserInfoDto() : UserInfoDao

    abstract fun getImageUrlDao() : ImageUrlDao

    abstract fun getPhotoDao() : PhotoWallDao

    abstract fun getHotWallDao() : HotWallDao

    abstract fun getGoodsListDao() : GoodsListDao

    abstract fun getHashDao() : HashTagDao

    abstract fun getCateDao() : CateDao
}