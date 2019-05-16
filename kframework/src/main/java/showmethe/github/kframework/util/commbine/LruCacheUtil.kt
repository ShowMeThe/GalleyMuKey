package showmethe.github.kframework.util.commbine

import android.graphics.Bitmap
import androidx.collection.LruCache
import android.util.Log


/**
 * showmethe.github.kframework.util.commbine
 * cuvsu
 * 2019/3/3
 **/
class LruCacheUtil {

    private var memoryCache: androidx.collection.LruCache<String, Bitmap>? = null

    init {
        val maxMemory = ((Runtime.getRuntime().maxMemory())/1024).toInt()
        val cacheSize  = (maxMemory/8)
        memoryCache = object : androidx.collection.LruCache<String, Bitmap>(cacheSize){
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.rowBytes * value.height / 1024;
            }
        }

    }


    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache!!.put(key, bitmap)
        }

    }


    fun getBitmapFromMemCache(key: String): Bitmap? {
        return memoryCache!!.get(key)
    }
}