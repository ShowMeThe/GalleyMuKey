package showmethe.github.kframework.http.coroutines

import retrofit2.Response

class Result<T>(status:String, response: T? = null,code:Int = -1,message:String = ""){

    companion object{

        const val Success = "Success"
        const val Failure = "Failure"
        const val OutTime = "OutTime"

    }

    init {

        var resp : ( (response: T?)->Unit)? = null
        fun response(response : (response: T?)->Unit){
            resp = response
        }
        var failed : ( (code: Int,message:String)->Unit)? = null
        fun failure(failure : (code: Int,message:String)->Unit){
            failed = failure
        }
        var over : ( ()->Unit)? = null
        fun outTime(outTime : ()->Unit){
            over = outTime
        }


        when (status) {
            Success -> {
                resp?.invoke(response)
            }
            Failure -> {
                failed?.invoke(code, message)
            }
            OutTime -> {
                over?.invoke()
            }
        }

    }





}