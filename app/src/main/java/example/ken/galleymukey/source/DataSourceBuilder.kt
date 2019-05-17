package example.ken.galleymukey.source

import android.content.Context
import androidx.room.Room
import example.ken.galleymukey.source.dao.UserInfoDao
import showmethe.github.kframework.util.rden.DatabaseCreator

/**
 * example.ken.galleymukey.source
 *
 * 2019/5/17
 **/
class DataSourceBuilder {

    companion object{

        private lateinit var creator : AppDataBaseCreator


        fun build(context : Context){
            creator = Room.databaseBuilder(context.applicationContext,AppDataBaseCreator::class.java,"app_database")
                .allowMainThreadQueries().build()
        }



        fun getUserDao() : UserInfoDao {
            return creator.getUserInfoDto()
        }


    }

}