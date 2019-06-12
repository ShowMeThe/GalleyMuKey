package showmethe.github.kframework.http.Interceptor

import android.util.Log


import java.io.IOException
import java.nio.charset.Charset

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer


class RequestLogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestBody = request.body()
        val responseBody = response.body()
        val responseBodyString = responseBody!!.string()
        var requestMessage: String
        requestMessage = request.method() + ' '.toString() + request.url()

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            requestMessage += "?\n" + buffer.readString(UTF8)
        }
        Log.e("RequestLogInterceptor", requestMessage)
        Log.e("RequestLogInterceptor", request.method() + ' '.toString() + request.url() + ' '.toString() + responseBodyString)
        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(),
                responseBodyString.toByteArray())).build()
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")
    }


}