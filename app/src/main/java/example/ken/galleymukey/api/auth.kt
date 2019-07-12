package example.ken.galleymukey.api

import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.pojo.Userdata
import retrofit2.Response
import retrofit2.http.*
import showmethe.github.kframework.http.JsonResult

interface auth {


    @POST("/user/register")
    suspend fun register(@Body bean: LoginBean) : Response<JsonResult<Userdata>>

}