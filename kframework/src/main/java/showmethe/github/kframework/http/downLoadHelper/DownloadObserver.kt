package showmethe.github.kframework.http.downLoadHelper

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * PackageName: example.ken.com.library.http.DownLoadHelper
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 17:03
 */
abstract class DownloadObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        onDownloadSuccess(t)
    }

    override fun onError(e: Throwable) {
        onDownloadError(e)
    }

    override fun onComplete() {

    }


    protected abstract fun onDownloadSuccess(t: T)

    protected abstract fun onDownloadError(e: Throwable)
}
