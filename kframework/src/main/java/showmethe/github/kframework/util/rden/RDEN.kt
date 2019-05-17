package showmethe.github.kframework.util.rden

import android.content.Context
import androidx.room.Room
import org.jetbrains.annotations.NotNull

/**
 * com.example.ken.kmvvm
 * cuvsu
 * 2019/5/13
 */
class RDEN private constructor(){

   companion object{


       private lateinit var creator : DatabaseCreator


       fun build(context : Context){
           creator = Room.databaseBuilder(context.applicationContext,DatabaseCreator::class.java,"roombean")
                   .allowMainThreadQueries().build()
       }


       fun getRoomDao() : RoomDao {
           return creator.roomDao()
       }


       fun put(@NotNull key: String,@NotNull value : String){
           val bean = RoomBean()
           bean.storeKey = key
           bean.stringValue = value
           getRoomDao().put(bean)
       }


       fun put(@NotNull key: String,@NotNull value : Boolean){
           val bean = RoomBean()
           bean.storeKey = key
           bean.booleanValue = value
           getRoomDao().put(bean)
       }


       fun put(@NotNull key: String,@NotNull value : Int){
           val bean = RoomBean()
           bean.storeKey = key
           bean.integerValue = value
           getRoomDao().put(bean)
       }


       fun put(@NotNull key: String,@NotNull value : Long){
           val bean = RoomBean()
           bean.storeKey = key
           bean.longValue = value
           getRoomDao().put(bean)
       }


       fun put(@NotNull key: String,@NotNull value : ByteArray){
           val bean = RoomBean()
           bean.storeKey = key
           bean.bytesValue = value
           getRoomDao().put(bean)
       }


       inline fun <reified T>get(key : String,default: T) : T? {
           try {
               val clazz = T::class.java
               if(clazz.name.equals(String :: class.java.name)){
                   val bean =  getRoomDao().get(key)
                   return bean?.stringValue as T
               }else  if(clazz.name == "java.lang.Boolean"){
                   val bean =  getRoomDao().get(key)
                   return bean?.booleanValue as T
               } else if(clazz.name == "java.lang.Integer"){
                   val bean =  getRoomDao().get(key)
                   return bean?.integerValue as T
               }else if(clazz.name == "java.lang.Long"){
                   val bean =  getRoomDao().get(key)
                   return bean?.longValue as T
               }else if(clazz.name.equals(ByteArray :: class.java.name)){
                   val bean =  getRoomDao().get(key)
                   return bean?.bytesValue as T
               }
           }catch (e : Exception){
               return default
           }
           return default
       }

   }




}
