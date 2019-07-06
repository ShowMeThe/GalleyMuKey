package example.ken.galleymukey.api

import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import showmethe.github.kframework.http.JsonResult

interface mine {

    @FormUrlEncoded
    @POST("/user/updateInfo")
    suspend fun updateInfo(@FieldMap map:MutableMap<String,Any>) : Response<JsonResult<String>>


}