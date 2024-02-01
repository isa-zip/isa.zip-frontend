package com.example.zipfront

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.*
interface LocationService {
    @GET("location")
    fun getLocation() : Call<List<Location>>
}