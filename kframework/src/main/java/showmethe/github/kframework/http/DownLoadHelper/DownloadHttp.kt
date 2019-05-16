package showmethe.github.kframework.http.DownLoadHelper

import android.content.Context

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 16:53
 */
class DownloadHttp private constructor(context: Context) {
    private val mRetrofit: Retrofit

    init {
        val httpCacheDirectory = File(context.applicationContext.cacheDir, context
                .applicationContext.packageName)
        val cacheSize = 15 * 1024 * 1024 // 15 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        val client = OkHttpClient.Builder()
                .addInterceptor(DownloadInterceptor())
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置连接超时时间
                .cache(cache)
                .build()
        mRetrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun <T> createApi(tClass: Class<T>): T {
        return mRetrofit.create(tClass)
    }

    companion object {

        var baseUrl = "https://wwww.baidu.com/"
       lateinit var http: DownloadHttp

        private val CONNECT_TIMEOUT = 30
        private val READ_TIMEOUT = 30
        private val WRITE_TIMEOUT = 30


        fun initHttp(context: Context) {
            http = DownloadHttp(context)
        }
    }
}
