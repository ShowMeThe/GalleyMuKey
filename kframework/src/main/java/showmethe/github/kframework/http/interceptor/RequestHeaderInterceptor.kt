package showmethe.github.kframework.http.interceptor


import java.io.IOException

import okhttp3.Interceptor
import showmethe.github.kframework.http.SessionObserver

class RequestHeaderInterceptor : Interceptor, SessionObserver {

    override fun update(sessionId: String) {
        this.sessionId = sessionId
    }


    var sessionId = ""
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val originalHttpUrl = request.url
        val htbuilder = originalHttpUrl.newBuilder()


        val url = htbuilder.build()
        val builder = request.newBuilder().url(url)


        if(this.sessionId.isEmpty()){
           // this.sessionId = Hawk.get(HawkConstant.SESSION, "")
        }
        builder.addHeader("sessionId", sessionId)
        builder.addHeader("Content-Type","application/json")
        builder.addHeader("Accept","application/json")
        return chain.proceed(builder.build())
    }
}