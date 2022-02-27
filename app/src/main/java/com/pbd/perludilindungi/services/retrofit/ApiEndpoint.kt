package com.pbd.perludilindungi.services.retrofit

import com.pbd.perludilindungi.FaskesModel
import com.pbd.perludilindungi.NewsModel
import com.pbd.perludilindungi.ProvinceCityModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("get-news")
    fun getNews(): Call<NewsModel>
    @GET("get-province")
    fun getProvince(): Call<ProvinceCityModel>

    @GET("get-city")
    fun getCity(
        @Query("start_id") start_id: String
    ): Call<ProvinceCityModel>

    @GET("get-faskes-vaksinasi")
    fun getFaskes(
        @Query("province") province: String,
        @Query("city") city : String
    ): Call<FaskesModel>
}