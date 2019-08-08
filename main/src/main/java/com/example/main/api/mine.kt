package com.example.main.api

import com.example.database.source.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import showmethe.github.kframework.http.JsonResult

interface mine {


    @POST("/user/updateInfo")
    suspend fun updateInfo(@Body userInfoDto: UserInfoDto) : Response<JsonResult<String>>


}