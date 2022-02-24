package com.pbd.perludilindungi.retrofit

import com.pbd.perludilindungi.NewsModel
import com.pbd.perludilindungi.ProvinceCityModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("get-news")
    fun getNews(): Call<NewsModel>
    @GET("get-province")
    fun getProvince(): Call<ProvinceCityModel>
    @GET("get-city")
    fun getCity(): Call<ProvinceCityModel>
}