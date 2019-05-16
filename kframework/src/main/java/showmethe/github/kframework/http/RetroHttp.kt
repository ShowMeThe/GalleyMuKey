package showmethe.github.kframework.http

import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter


import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import showmethe.github.kframework.base.BaseApplication

/**
 *
 * Author : jiaqi Ye
 * Date : 2018/5/7
 * Time : 14:54
 */

class RetroHttp  private constructor() : SessionObservable{

    override fun notify(session: String) {
        headerInterceptor.update(session)
       // Hawk.get(HawkConstant.SESSION, session)
    }



    private val mRetrofit: Retrofit
   private val headerInterceptor = RequestHeaderInterceptor()

    init {
        val httpCacheDirectory = File(BaseApplication.context.externalCacheDir, BaseApplication.context.packageName)
        val cacheSize = 15 * 1024 * 1024 // 15 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(RequestLogInterceptor())
                .addNetworkInterceptor(ReadWriteCacheInterceptor())
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置连接超时时间
                .cache(cache)
                .build()
        val gson = GsonBuilder()
                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<Any>())
                .registerTypeAdapterFactory(NullbooleanTofalseAdapterFactory<Any>())
                .registerTypeAdapterFactory(NulldoubleTo0AdapterFactory<Any>())
                .registerTypeAdapterFactory(NullIntTo0AdapterFactory<Any>())
                .registerTypeAdapterFactory(NullLongTo0AdapterFactory<Any>())
                .serializeNulls()
                .create()
        mRetrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    private fun <T> createApi(tClass: Class<T>): T {
        return mRetrofit.create(tClass)
    }


    //自定义Strig适配器
    private class NullStringToEmptyAdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType as Class<T>
            return if (rawType != String::class.java) {
                null
            } else STRING as TypeAdapter<T>
        }
    }

    //自定义LONG适配器
    private class NullLongTo0AdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType as Class<T>
            return if (rawType != Long::class.java) {
                null
            } else LONG as TypeAdapter<T>
        }
    }


    //自定义Int适配器
    private class NullIntTo0AdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType as Class<T>
            return if (rawType != Int::class.java) {
                null
            } else INT as TypeAdapter<T>
        }
    }

    //自定义double适配器
    private class NulldoubleTo0AdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType as Class<T>
            return if (rawType != Double::class.java) {
                null
            } else DOUBLE as TypeAdapter<T>
        }
    }

    //自定义boolean适配器
    private class NullbooleanTofalseAdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            val rawType = type.rawType as Class<T>
            return if (rawType != Boolean::class.java) {
                null
            } else BOOLEAN as TypeAdapter<T>
        }
    }

    companion object {

       private var baseUrl = "https://www.baidu.com/"


        private val CONNECT_TIMEOUT = 30
        private val READ_TIMEOUT = 30
        private val WRITE_TIMEOUT = 30

        private val INSTANT : RetroHttp by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ RetroHttp() }

        fun get() : RetroHttp{
            return INSTANT
        }

        fun <T> createApi(tClass: Class<T>): Lazy<T> {
            return  lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ RetroHttp.get().createApi(tClass) }
        }


        private val STRING = object : TypeAdapter<String>() {
            @Throws(IOException::class)
            override fun read(reader: JsonReader): String {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return ""
                }
                return reader.nextString()
            }

            @Throws(IOException::class)
            override fun write(writer: JsonWriter, value: String?) {
                if (value == null) {
                    // 在这里处理null改为空字符串
                    writer.value("")
                    return
                }
                writer.value(value)
            }
        }
        private val LONG = object : TypeAdapter<Long>() {
            @Throws(IOException::class)
            override fun read(reader: JsonReader): Long {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return 0L
                }
                return reader.nextLong()
            }

            @Throws(IOException::class)
            override fun write(writer: JsonWriter, value: Long?) {
                if (value == null) {
                    writer.value(0L)
                    return
                }
                writer.value(value)
            }
        }
        private val INT = object : TypeAdapter<Int>() {
            @Throws(IOException::class)
            override fun read(reader: JsonReader): Int {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return 0
                }
                return reader.nextInt()
            }

            @Throws(IOException::class)
            override fun write(writer: JsonWriter, value: Int?) {
                if (value == null) {
                    writer.value(0)
                    return
                }
                writer.value(value)
            }
        }

        private val DOUBLE = object : TypeAdapter<Double>() {
            @Throws(IOException::class)
            override fun read(reader: JsonReader): Double {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return 0.0
                }
                return reader.nextDouble()
            }

            @Throws(IOException::class)
            override fun write(writer: JsonWriter, value: Double?) {
                if (value == null) {
                    writer.value(0.0)
                    return
                }
                writer.value(value)
            }
        }

        private val BOOLEAN = object : TypeAdapter<Boolean>() {
            @Throws(IOException::class)
            override fun read(reader: JsonReader): Boolean {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return false
                }
                return reader.nextBoolean()
            }

            @Throws(IOException::class)
            override fun write(writer: JsonWriter, value: Boolean?) {
                if (value == null) {
                    writer.value(false)
                    return
                }
                writer.value(value)
            }
        }
    }
}
