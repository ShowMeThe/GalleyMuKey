package example.ken.galleymukey.api

import example.ken.galleymukey.pojo.Userdata
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import showmethe.github.kframework.http.JsonResult

interface auth {

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("phone") account:String,@Field("password") password:String) : Response<JsonResult<Userdata>>

}