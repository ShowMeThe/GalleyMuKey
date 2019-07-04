package showmethe.github.kframework.util.rden

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

       fun put(@NotNull key: String,@NotNull value : ArrayList<*>){
           val bean = RoomBean()
           bean.storeKey = key
           bean.listValue = value
           getRoomDao().put(bean)
       }


       inline fun <reified T>get(key : String,default: T) : T {
           try {
               val clazz = T::class.java
               if(clazz.name == String :: class.java.name){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.stringValue.isNullOrEmpty()){
                       default
                   }else{
                       bean?.stringValue as T
                   }
               }else  if(clazz.name == "java.lang.Boolean"){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.booleanValue == null){
                       default
                   }else{
                       bean.booleanValue as T
                   }
               } else if(clazz.name == "java.lang.Integer"){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.integerValue == null){
                       default
                   }else{
                       bean.integerValue as T
                   }
               }else if(clazz.name == "java.lang.Long"){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.longValue == null){
                       default
                   }else{
                       bean.longValue as T
                   }
               }else if(clazz.name == ByteArray :: class.java.name){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.bytesValue == null){
                       default
                   }else{
                       bean.bytesValue as T
                   }
               }else if(clazz.name == "java.util.ArrayList"){
                   val bean =  getRoomDao().get(key)
                   return if(bean?.listValue == null){
                       default
                   }else{
                       bean.listValue as T
                   }
               }
           }catch (e : Exception){
               return default
           }
           return default
       }


      private fun stringToObject(value: String): ArrayList<String>{
           val listType = object : TypeToken<ArrayList<String>>() {
           }.type
           return  Gson().fromJson(value, listType)
       }


       private fun objectToString(list: ArrayList<String>): String {
           val gson = Gson()
           return gson.toJson(list)
       }

   }
}
