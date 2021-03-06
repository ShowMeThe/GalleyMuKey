package showmethe.github.kframework.http

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
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
import showmethe.github.kframework.http.interceptor.ReadWriteCacheInterceptor
import showmethe.github.kframework.http.interceptor.RequestHeaderInterceptor
import showmethe.github.kframework.http.interceptor.RequestLogInterceptor

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
                .registerTypeAdapter(String::class.java, STRING)
                .registerTypeAdapter(Long::class.java, LONG)
                .registerTypeAdapter(Boolean::class.java, BOOLEAN)
                .registerTypeAdapter(Int::class.java, INT)
                .registerTypeAdapter(Double::class.java, DOUBLE)
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




    companion object {

       private var baseUrl = "http://192.168.42.113:8081/"


        private val CONNECT_TIMEOUT = 30
        private val READ_TIMEOUT = 30
        private val WRITE_TIMEOUT = 30

        private val INSTANT : RetroHttp by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ RetroHttp() }

        fun get() : RetroHttp{
            return INSTANT
        }

        fun <T> createApi(tClass: Class<T>): T {
            return  lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ RetroHttp.get().createApi(tClass) }.value
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
