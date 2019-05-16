package showmethe.github.kframework.util.rden

import androidx.room.Database
import androidx.room.RoomDatabase
import showmethe.github.kframework.util.rden.RoomBean
import showmethe.github.kframework.util.rden.RoomDao

/**
 * com.example.ken.kmvvm
 *
 * 2019/5/13
 **/
@Database(entities = arrayOf(RoomBean::class),version = 1)
abstract class DatabaseCreator : RoomDatabase() {

   abstract fun roomDao() : RoomDao

}