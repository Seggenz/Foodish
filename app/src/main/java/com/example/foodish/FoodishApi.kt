package com.example.foodish

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class FoodishResponse(
    val image: String
)

interface FoodishApi {
    @GET("/api/images/{category}")
    fun getImage(@Path("category") category: String): Call<FoodishResponse>
}