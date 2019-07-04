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
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil
import java.io.IOException

/**
 * showmethe.github.kframework.http.Coroutines
 * cuvsu
 * 2019/6/7
 **/

class CallResult<T> constructor(var owner: LifecycleOwner?) {


    private lateinit var job :Deferred<Response<JsonResult<T>>>
    private  lateinit var response : Response<JsonResult<T>>
    private var netJob : Job? = null

    fun hold(result:()-> Call<JsonResult<T>> ) : CallResult<T>{
        owner?.apply {
            netJob =   lifecycleScope.launchWhenStarted{
                    withContext(Dispatchers.Main){
                        onLoading?.invoke()
                    }
                    withContext(Dispatchers.IO){
                        job = async {    result.invoke().execute() }
                        response = job.await()
                    }
                    if(lifecycle.currentState != Lifecycle.State.DESTROYED){
                        withContext(Dispatchers.Main) {
                            build()
                        }
                    }else{
                        job.cancel()
                    }
            }
        }
        return this
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
       netJob?.cancel()
    }


    private var  onLoading : (() ->Unit)?  = null

    fun loading(onLoading : (() ->Unit)) :  CallResult<T> {
        this.onLoading = onLoading
        return  this
    }

    private var  onSuccess : ((response: T?, message: String) ->Unit)?  = null

    private var onError : ((code :Int,message:String)->Unit)? = null

    fun success(onSuccess : ((response: T?, message: String) ->Unit)) :  CallResult<T>{
        this.onSuccess = onSuccess
        return this
    }


    fun error(onError : ((code :Int,message:String)->Unit)) : CallResult<T>{
        this.onError = onError
        return this
    }



}