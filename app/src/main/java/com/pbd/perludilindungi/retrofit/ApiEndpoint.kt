package com.pbd.perludilindungi.retrofit

import com.pbd.perludilindungi.NewsModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("get-news")
    fun getNews(): Call<NewsModel>
}