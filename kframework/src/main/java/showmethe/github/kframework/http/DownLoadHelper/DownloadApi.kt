package showmethe.github.kframework.http.DownLoadHelper

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 17:38
 */
interface DownloadApi {

    /**
     * 下载
     */
    @Streaming
    @GET
    fun download(@Url url: String): Observable<ResponseBody> //直接使用网址下载


}
