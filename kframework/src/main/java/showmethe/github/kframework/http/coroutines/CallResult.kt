package showmethe.github.kframework.http.coroutines

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import io.reactivex.annotations.NonNull
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil
import showmethe.github.kframework.http.RetroHttp
import java.io.IOException

/**
 * showmethe.github.kframework.http.Coroutines
 * cuvsu
 * 2019/6/7
 **/

class CallResult<T> constructor(var owner: LifecycleOwner?) {

    private var response: Response<JsonResult<T>>? = null
    private var netJob: Job? = null

    fun hold(result: () -> Call<JsonResult<T>>): CallResult<T> {
        owner?.apply {
            netJob = lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.Main) {
                    onLoading?.invoke()
                }
                withContext(Dispatchers.IO) {
                    withTimeout(5000) {
                        val requests = result.invoke()
                        try {
                            response = requests.execute()
                        } catch (e: Exception) {
                            loadingOutTime?.invoke(Result(Result.OutTime))
                            Log.e("CallResult", "${e.message}")
                        }
                    }
                }
                if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            build()
                        } else {
                            loadingOutTime?.invoke(Result(Result.OutTime))
                        }
                    }
                } else {
                    loadingOutTime?.invoke(Result(Result.OutTime))
                    netJob?.cancel()
                }
            }
        }
        return this
    }

    private fun build() {
        response?.apply {
            if (!isSuccessful) {
                try {
                    val result = JsonUtil.fromJson(errorBody().toString(), JsonResult::class.java)
                    if (result != null) {
                        val errorMessage = result.message!!
                        onError?.invoke(Result(Result.Failure, null, -1, errorMessage), -1, errorMessage)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    onError?.invoke(Result(Result.Failure, null, -1, e.message.toString()), -1, e.message.toString())
                }
            } else {
                try {
                    if (body() == null) {
                        onError?.invoke(Result(Result.Failure, null, -1, ""), -1, "")
                    } else {
                        if (body()?.code == 1) {
                            onSuccess?.invoke(
                                Result(Result.Success, body()?.data, body()?.code!!, body()?.message!!),
                                body()?.message!!
                            )
                        } else {
                            onError?.invoke(
                                Result(Result.Failure, null, body()?.code!!, body()?.message!!),
                                body()?.code!!,
                                body()?.message!!
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            netJob?.cancel()
        }
    }


    private var onLoading: (() -> Unit)? = null

    fun loading(onLoading: (() -> Unit)): CallResult<T> {
        this.onLoading = onLoading
        return this
    }

    private var loadingOutTime: ((result: Result<T>) -> Unit)? = null
    fun outTime(loadingOutTime: ((result: Result<T>) -> Unit)) {
        this.loadingOutTime = loadingOutTime
    }


    private var onSuccess: ((result: Result<T>, message: String) -> Unit)? = null

    private var onError: ((result: Result<T>, code: Int, message: String) -> Unit)? = null

    fun success(onSuccess: ((result: Result<T>, message: String) -> Unit)): CallResult<T> {
        this.onSuccess = onSuccess
        return this
    }


    fun error(onError: ((result: Result<T>, code: Int, message: String) -> Unit)): CallResult<T> {
        this.onError = onError
        return this
    }


}