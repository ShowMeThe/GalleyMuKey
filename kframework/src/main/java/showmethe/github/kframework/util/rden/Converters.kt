package showmethe.github.kframework.util.rden

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * com.example.database.source.converters
 * cuvsu
 * 2019/5/17
 **/
class Converters {

    @TypeConverter
    fun stringToObject(value: String): ArrayList<*>{
        if(value.isEmpty()){
            return ArrayList<String>()
        }else{
            val listType = object : TypeToken<ArrayList<*>>() {
            }.type
            return  Gson().fromJson(value, listType)
        }
    }

    @TypeConverter
    fun objectToString(list: ArrayList<*>?): String {
        if(list.isNullOrEmpty()){
            return "[]"
        }else{
            val gson = Gson()
            return gson.toJson(list)
        }
    }

}