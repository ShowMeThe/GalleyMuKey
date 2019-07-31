package showmethe.github.kframework.http.downLoadHelper

import android.util.Log

import java.io.IOException

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 16:54
 */
class DownloadResponseBody(private val responseBody: ResponseBody) : ResponseBody() {

    // BufferedSource OKIO库输入流
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource{
        if (bufferedSource == null) {
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    /**
     * 处理数据
     * @param source 数据源
     * @return 返回处理后的数据源
     */
    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                Log.e("download", "read: " + (totalBytesRead * 100 / responseBody.contentLength()).toInt())
                return bytesRead
            }
        }

    }


}
