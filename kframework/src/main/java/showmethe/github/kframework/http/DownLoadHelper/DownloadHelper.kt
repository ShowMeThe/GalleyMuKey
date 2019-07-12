package showmethe.github.kframework.http.DownLoadHelper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle

import java.io.File
import java.io.IOException

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okio.*

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 17:40
 */
class DownloadHelper private constructor() {

    private val api: DownloadApi = DownloadHttp.http.createApi(DownloadApi::class.java)


    fun singleDownLoad(owner: LifecycleOwner, downUrl: String, saveDir: String, fileName: String, listener: ProgressListener) {

        val provider = AndroidLifecycle.createLifecycleProvider(owner)
        api.download(downUrl).map { responseBody -> responseBody }.compose { upstream ->
            upstream.compose(provider.bindUntilEvent(Lifecycle.Event.ON_DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }.subscribe(object : DownloadObserver<ResponseBody>() {
            override fun onDownloadSuccess(t: ResponseBody) {
                try {
                    saveFile(t, saveDir, fileName, listener)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onDownloadError(e: Throwable) {
                listener.onFail(e)
            }
        })

    }


    @Throws(IOException::class)
    private fun saveFile(responseBody: ResponseBody, saveDir: String, fileName: String, listener: ProgressListener): File {
        val bufferSize = 200 * 1024
        var len: Long
        var sum: Long = 0
        var sink: BufferedSink? = null
        var source: BufferedSource? = null
        try {
            val dir = File(saveDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, fileName)
            if (file.exists()) {
                file.delete()
            }
            sink = file.sink().buffer()
            val buffer = sink.buffer
            source = responseBody.source()

            while (source.read(buffer, bufferSize.toLong())
                            .also {
                        len = it }!=-1L){
                sum += len
                sink.emit()
                listener.onProgress((sum * 100 / responseBody.contentLength()).toInt())
            }
            return file
        } finally {
            source!!.close()
            sink!!.flush()
            sink.close()
        }
    }


    companion object {
        fun get(): DownloadHelper {
            val helper: DownloadHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                DownloadHelper()
            }
            return helper
        }
    }


}
