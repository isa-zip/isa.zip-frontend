package com.example.zipfront.connection

import com.google.gson.annotations.SerializedName

class RetrofitClient2 {
    //로그인
    data class Requestlogin(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String
    )

    data class Responselogin(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: LoginResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )
    data class LoginResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )
}