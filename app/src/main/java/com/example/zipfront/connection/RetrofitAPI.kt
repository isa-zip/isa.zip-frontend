package com.example.zipfront.connection

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitAPI {
    @POST("/auth/login")
    fun login(@Body request: RetrofitClient2.Requestlogin): Call<RetrofitClient2.Responselogin>

    @DELETE("/users")
    fun withdraw(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseWithdraw>


    @POST("/auth/sign-up")
    fun setting(@Body request: RetrofitClient2.Requestsetting): Call<RetrofitClient2.Responsesetting>

    @POST("/users/schedule")
    fun schedule(@Header("Authorization") token: String): Call<RetrofitClient2.Responseschedule>

    //카카오맵 검색
    @GET("v2/local/search/address.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키 [필수]
        @Query("query") query: String, // 검색을 원하는 질의어 [필수]
    ): Call<RetrofitClient2.ResultSearchKeyword> // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김

}