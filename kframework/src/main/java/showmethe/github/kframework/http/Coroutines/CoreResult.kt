package showmethe.github.kframework.http.Coroutines

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.annotations.NonNull
import retrofit2.Response
import showmethe.github.kframework.http.JsonResult
import showmethe.github.kframework.http.JsonUtil
import java.io.IOException

/**
 * showmethe.github.kframework.http.Coroutines
 * cuvsu
 * 2019/6/7
 **/

class CoreResult<T> constructor(var  response: Response<JsonResult<T>>,var owner: LifecycleOwner?) {


    fun build(){
        owner?.apply {
            if(lifecycle.currentState != Lifecycle.State.DESTROYED){
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
                                Log.e("222222222","${response.body()?.data}")
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
        }

    }

    private var  onSuccess : ((response: T?, message: String) ->Unit)?  = null

    private var onError : ((code :Int,message:String)->Unit)? = null

    fun success(onSuccess : ((response: T?, message: String) ->Unit)) :  CoreResult<T>{
        this.onSuccess = onSuccess
        return this
    }


    fun error(onError : ((code :Int,message:String)->Unit)) : CoreResult<T>{
        this.onError = onError
        return this
    }



}