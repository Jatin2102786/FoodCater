package com.example.emergency.respository.networkrequest

import com.example.emergency.respository.models.LoginResponseModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("login")
    fun login(
        @FieldMap login: MutableMap<String, String?>
    ): Observable<Response<LoginResponseModel>>
}