package com.example.main.api

import com.example.database.bean.LoginBean
import com.example.database.pojo.Userdata
import retrofit2.Response
import retrofit2.http.*
import showmethe.github.kframework.http.JsonResult

interface auth {


    @POST("/user/register")
    suspend fun register(@Body bean: LoginBean) : Response<JsonResult<Userdata>>

}