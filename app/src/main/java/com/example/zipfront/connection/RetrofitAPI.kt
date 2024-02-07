package com.example.zipfront.connection

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("/auth/login")
    fun login(@Body request: RetrofitClient2.Requestlogin): Call<RetrofitClient2.Responselogin>

    @DELETE("/users")
    fun withdraw(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseWithdraw>
}