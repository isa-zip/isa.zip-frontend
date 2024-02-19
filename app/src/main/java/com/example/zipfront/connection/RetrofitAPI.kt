package com.example.zipfront.connection

import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @POST("/auth/login")
    fun login(@Body request: RetrofitClient2.Requestlogin): Call<RetrofitClient2.Responselogin>

    @POST("/users/logout")
    fun logout(@Header("Authorization") token: String): Call<RetrofitClient2.Responselogout>

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
                 @Query("dong") dong: String, // 쿼리 매개변수 추가
                 @Body request: RetrofitClient2.RequestUseritem): Call<RetrofitClient2.ResponseUseritem>

    @GET("/match/users/items")
    fun usermatchitem(@Header("Authorization") token: String,
                      @Query("matchStatus") matchStatus: String): Call<RetrofitClient2.ResponseUserMatchitem>

    @GET("/brokers/items/show")
    fun brokeritem(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseBrokeritem>

    @GET("/match/brokers/items")
    fun matchbrokeritem(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseMatchbrokeritem>

    @GET("/home")
    fun home(@Header("Authorization") token: String): Call<RetrofitClient2.ResponseHome>

    @POST("/match/brokers/{userItemId}")
    fun matchBrokerItem(
        @Header("Authorization") token: String,
        @Path("userItemId") userItemId: Int,
        @Body request: RetrofitClient2.RequestMatchbroker
    ): Call<RetrofitClient2.ResponseMatchbroker>

    @PATCH("/match/status")
    fun matchBrokeUserItem(
        @Header("Authorization") token: String,
        @Body request: RetrofitClient2.RequestMatchbroker2
    ): Call<RetrofitClient2.ResponseMatchbroker2>

    @POST("/auth/sign-up")
    fun setting(@Body request: RetrofitClient2.Requestsetting): Call<RetrofitClient2.Responsesetting>

    // 일정 등록
    @POST("/users/schedule")
    fun schedule(
        @Header("Authorization") token: String,
        @Body request: RetrofitClient2.Requestschedule
    ): Call<RetrofitClient2.Responseschedule>

    // 일정 수정
    @PUT("/users/schedule")
    fun schedulemodify(
        @Header("Authorization") token: String,
        @Body request: RetrofitClient2.Requestschedulemodify
    ): Call<RetrofitClient2.Responseschedulemodify>

    // 일정 삭제
    @DELETE("/users/schedule")
    fun scheduledelete(
        @Header("Authorization") token: String
    ): Call<RetrofitClient2.Responsescheduledelete>

    // 일정 조회
    @GET("/users/schedule")
    fun schedulelookup(
        @Header("Authorization") token: String
    ): Call<RetrofitClient2.Responseschedulelookup>

    // 상세 일정 조회
    @GET("/users/events")
    fun evenschedulelookup(
        @Header("Authorization") token: String
    ): Call<RetrofitClient2.ResponseEventScheduleLookup>

    // 상세 일정 수정
    @PUT("/users/events/{eventId}")
    fun evenschedulemodify(
        @Header("Authorization") token: String,
        @Body request: RetrofitClient2.RequestEventschedulemodify
    ): Call<RetrofitClient2.ResponseEventschedulemodify>

    // 상세 일정 삭제
    @DELETE("/users/events/{eventId}")
    fun evenscheduledelete(
        @Header("Authorization") token: String,
        @Body request: RetrofitClient2.RequestEventscheduledelete
    ): Call<RetrofitClient2.ResponseEventscheduledelete>

    // 매물 새로 등록하기 전 주소 입력
    @GET("/brokers/map") // 변경된 주소로 수정
    fun BeforeAddress(
        @Header("Authorization") token: String,
        @Query("address") address: String
    ): Call<RetrofitClient2.ResponseBeforeAddress>

    // 매물 새로 등록하기(공인중개사)
    @Multipart
    @POST("/brokers/items")
    fun NewItem(
        @Header("Authorization") token: String,
        @Query("address") address: String,
        @Part("detailsRequest") detailsRequest: RequestBody,
        @Part("optionsRequest") optionsRequest: RequestBody,
        @Part multipartFiles: MultipartBody.Part?
    ): Call<RetrofitClient2.ResponseProfile>

    //카카오맵 검색
    @GET("v2/local/search/address.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키 [필수]
        @Query("query") query: String, // 검색을 원하는 질의어 [필수]
    ): Call<RetrofitClient2.ResultSearchKeyword> // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김

    //매물단건 상세조회
    @GET("/brokers/items/show/details/{brokerItemId}")
    fun showDetailItem(
        @Header("Authorization") token: String,
        @Path("brokerItemId") brokerItemId: Int
    ): Call<RetrofitClient2.ResponseDetail>

    //매물 삭제
    @DELETE("/brokers/items/{brokerItemId}")
    fun deleteProperty(
        @Header("Authorization") token: String,
        @Path("brokerItemId") brokerItemId: Int
    ) : Call<RetrofitClient2.ResponseDelete>

    //매물 판매완료
    @PATCH("/brokers/items/{brokerItemId}/soldout")
    fun soldOutProperty(
        @Header("Authorization") token: String,
        @Path("brokerItemId") brokerItemId: Int
    ) : Call<RetrofitClient2.ResponseSoldOut>

    //매물 탭 메인
    @GET("/main/item")
    fun showPropertyItem(
        @Header("Authorization") token: String,
        @Query("x") x: Double,
        @Query("y") y: Double
    ): Call<RetrofitClient2.ResponseLocationFilter>


    //매물 수정
    /*@PUT("/brokers/items/{brokerItemId}")
    @Multipart
    fun modifyDetailProperty(
        @Header("Authorization") token: String,
        @Path("brokerItemId") brokerItemId: Int,
        @Part
    ): Call<RetrofitClient2.ResponseDetail>*/
}