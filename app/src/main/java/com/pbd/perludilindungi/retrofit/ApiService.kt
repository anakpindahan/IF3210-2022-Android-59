package com.pbd.perludilindungi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    val BASE_URL = "https://perludilindungi.herokuapp.com/api/"
    val endpoint: ApiEndpoint
    get(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(ApiEndpoint::class.java)
    }

}