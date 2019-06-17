package showmethe.github.kframework.http.Coroutines

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import retrofit2.Response
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil


class SuspendResult<T> constructor(var owner: LifecycleOwner?) {


    private  lateinit var response : Response<JsonResult<T>>
    fun hold(block: suspend () -> Response<JsonResult<T>>){
        owner?.apply {
            GlobalScope.launch (Dispatchers.IO){
                launch (Dispatchers.Main){
                    onLoading?.invoke()
                }
                response =  block.invoke()
                launch (Dispatchers.Main){
                    build()
                }
            }
        }
    }


    private fun build(){
        if(!response.isSuccessful){
            try {
                val result = JsonUtil.fromJson(response.errorBody().toString(), JsonResult::class.java)
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
                if (response.body() == null) {
                    onError?.invoke(-1, "")
                } else {
                    if (response.body()?.code == 2000000) {
                        onSuccess?.invoke(response.body()?.data, response.body()?.message!!)
                    } else {
                        onError?.invoke(response.body()?.code!!, response.body()?.message!!)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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