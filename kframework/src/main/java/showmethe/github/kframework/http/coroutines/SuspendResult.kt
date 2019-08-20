package showmethe.github.kframework.http.coroutines

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.*
import retrofit2.Response
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil


class SuspendResult<T> constructor(var owner: LifecycleOwner?) {


    private  var response : Response<JsonResult<T>>? = null
    private var netJob : Job? = null

    fun hold(block: suspend () -> Response<JsonResult<T>>){
        owner?.apply {
            netJob =   lifecycleScope.launchWhenStarted{
                withContext(Dispatchers.Main){
                    onLoading?.invoke() }
                withTimeout(5000){
                    try {
                        response =  block.invoke()
                    } catch (e: Exception) {
                        Log.e("SuspendResult", "${e.message}")
                    }
                }
                withContext (Dispatchers.Main){
                     if(response!=null){
                         build()
                     }
                }
            }
        }
    }


    private fun build(){
        response?.apply {
            if(!isSuccessful){
                try {
                    val result = JsonUtil.fromJson(errorBody().toString(), JsonResult::class.java)
                    if (result != null) {
                        val errorMessage = result.message!!
                        onError?.invoke(-1,errorMessage)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    onError?.invoke(-1,e.message.toString())
                }
            }else{
                try {
                    if (body() == null) {
                        onError?.invoke(-1, "")
                    } else {
                        if (body()?.resultCode == 1) {
                            onSuccess?.invoke(body()?.data, body()?.message!!)
                        } else {
                            onError?.invoke(body()?.resultCode!!, body()?.message!!)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            netJob?.cancel()
        }
    }

    private var  onLoading : (() ->Unit)?  = null

    fun loading(onLoading : (() ->Unit)) :  SuspendResult<T> {
        this.onLoading = onLoading
        return  this
    }

    private var  onSuccess : ((response: T?, message: String) ->Unit)?  = null

    private var onError : ((code :Int,message:String)->Unit)? = null

    fun success(onSuccess : ((response: T?, message: String) ->Unit)) :  SuspendResult<T>{
        this.onSuccess = onSuccess
        return this
    }


    fun error(onError : ((code :Int,message:String)->Unit)) : SuspendResult<T>{
        this.onError = onError
        return this
    }

}