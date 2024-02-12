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

    // 이메일 회원가입
    data class Requestsetting(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("nickName")
        val nickName: String
    )

    data class Responsesetting(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    // 이사 일정
    data class Requestschedule(
        @SerializedName("period")
        val email: String,
        @SerializedName("moveDate")
        val password: String
    )

    data class Responseschedule(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ScheduleResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ScheduleResult(
        @SerializedName("scheduleId")
        val scheduleId: String,
        @SerializedName("userId")
        val userId: String,
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: String
    )
}