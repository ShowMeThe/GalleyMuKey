package showmethe.github.kframework.http.DownLoadHelper

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 16:54
 */
class DownloadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder().body(
                response.body()?.let { DownloadResponseBody(it) })
                .build()
    }
}
