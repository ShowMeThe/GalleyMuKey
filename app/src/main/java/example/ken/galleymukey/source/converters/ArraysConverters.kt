package example.ken.galleymukey.source.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * example.ken.galleymukey.source.converters
 * cuvsu
 * 2019/5/17
 **/
class ArraysConverters {

    @TypeConverter
    fun stringToObject(value: String): ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>() {
        }.type
        return  Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}