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
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )

    data class ResponseWithdraw(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ResponseProfilesee(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ProfileseeResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ProfileseeResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("userImg")
        val userImg: String,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("email")
        val email: String
    )

    data class ResponseProfile(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ProfileResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ProfileResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("userImg")
        val userImg: String,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("email")
        val email: String
    )

    data class RequestCertify(
        @SerializedName("brokerId")
        val brokerId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("phoneNum")
        val phoneNum: String,
        @SerializedName("businessName")
        val businessName: String
    )

    data class ResponseCertify(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )
}