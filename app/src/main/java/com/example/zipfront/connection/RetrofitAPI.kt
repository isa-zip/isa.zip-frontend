package com.example.zipfront.connection

import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitAPI {
    @POST("/auth/login")
    fun login(@Body request: RetrofitClient2.Requestlogin): Call<RetrofitClient2.Responselogin>

    @DELETE("/users")
    fun withdraw(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseWithdraw>

    @GET("/users")
    fun profile(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseProfilesee>

    @Multipart
    @PUT("/users")
    fun editprofile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("nickName") nickName: RequestBody?,
        @Part("password") password: RequestBody
    ): Call<RetrofitClient2.ResponseProfile>

    @POST("/users/auth-broker")
    fun menucertify(@Header("Authorization") token: String,
                @Body request: RetrofitClient2.RequestCertify): Call<RetrofitClient2.ResponseCertify>

    @GET("/users/items/dong-count")
    fun dongcount(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseDongcount>

    @GET("/users/items")
    fun dongitem(@Header("Authorization") token: String,
                 @Query("dongName") dongName: String): Call<RetrofitClient2.ResponseDongitem>
    @POST("/users/items")
    fun useritem(@Header("Authorization") token: String,
                 @Query("address") address: String, // 쿼리 매개변수 추가
                 @Body request: RetrofitClient2.RequestUseritem): Call<RetrofitClient2.ResponseUseritem>

    @GET("/brokers/items/show")
    fun brokeritem(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseBrokeritem>

}