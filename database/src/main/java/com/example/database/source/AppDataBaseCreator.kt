package com.example.database.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.source.dao.*
import com.example.database.source.dto.*


/**
 * com.example.database.source.dto
 *
 * 2019/5/17
 **/
@Database(entities = arrayOf(UserInfoDto::class,
    ImageUrlDto::class,
    PhotoWallDto::class,
    HotWallDto::class,
    GoodsListDto::class,
    HashTagDto::class,
    CateDto::class,
    CommentDto::class,
    NewGoodsSellDto::class,
    CartListDto::class,OrderDto::class),version = 2)
abstract class AppDataBaseCreator : RoomDatabase() {

    abstract fun getUserInfoDto() : UserInfoDao

    abstract fun getImageUrlDao() : ImageUrlDao

    abstract fun getPhotoDao() : PhotoWallDao

    abstract fun getHotWallDao() : HotWallDao

    abstract fun getGoodsListDao() : GoodsListDao

    abstract fun getHashDao() : HashTagDao

    abstract fun getCateDao() : CateDao

    abstract fun getCommentDao() : CommentDao

    abstract fun getNewGoodsDao() : NewGoodsDao

    abstract fun getOrderDao() : OrderDao

}