package showmethe.github.kframework.http.downLoadHelper

import java.io.File

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 16:57
 */
interface ProgressListener {

    fun onProgress(progress: Int)

    fun onFinishDownload(file: File)

    fun onFail(ex: Throwable)


}
