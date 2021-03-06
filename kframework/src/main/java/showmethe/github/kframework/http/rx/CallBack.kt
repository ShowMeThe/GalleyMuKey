package showmethe.github.kframework.http.rx


import java.io.IOException

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil

/**
 * PackageName: com.library.activity
 * Author : jiaqi Ye
 * Date : 2018/5/7
 * Time : 14:54
 */

abstract class CallBack<T> : Observer<JsonResult<T>> {



    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        onPreLoading()
    }

    override fun onNext(t: JsonResult<T>) {
        if (t.code == 2000000) {
            success(t.data, t.message!!)
        } else {
            fail(t.code, t.message!!)
        }
    }

    override fun onError(e: Throwable) {
        var errorMessage = e.toString().replace("java.lang.Throwable:".toRegex(), "")
        if (e is HttpException) {
            val body = e.response()?.errorBody()
            try {
                val result = JsonUtil.fromJson(body!!.string(), JsonResult::class.java)
                if (result != null) {
                    errorMessage = result.message!!
                }

            } catch (ioe: IOException) {
                ioe.printStackTrace()
            }

        } else {
            e.printStackTrace()
        }
        fail(-1, errorMessage)

    }

    abstract fun onPreLoading()

    abstract fun success(response: T?, @NonNull message: String)

    abstract fun fail(@NonNull errCode: Int, @NonNull message: String)
}
