package showmethe.github.kframework.http

import android.util.Log


import java.io.IOException

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

class RequestHeaderInterceptor : Interceptor,SessionObserver{

    override fun update(sessionId: String) {
        this.sessionId = sessionId
    }


    var sessionId = ""
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val originalHttpUrl = request.url()
        val htbuilder = originalHttpUrl.newBuilder()


        val url = htbuilder.build()
        val builder = request.newBuilder().url(url)


        if(this.sessionId.isEmpty()){
           // this.sessionId = Hawk.get(HawkConstant.SESSION, "")
        }
        builder.addHeader("sessionId", sessionId)
        return chain.proceed(builder.build())
    }
}