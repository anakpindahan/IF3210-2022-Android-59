package com.pbd.perludilindungi.services.retrofit

import com.pbd.perludilindungi.FaskesModel
import com.pbd.perludilindungi.NewsModel
import com.pbd.perludilindungi.ProvinceCityModel
import com.pbd.perludilindungi.model.QrCodeResponseModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("api/get-news")
    fun getNews(): Call<NewsModel>
    @GET("api/get-province")
    fun getProvince(): Call<ProvinceCityModel>

    @GET("api/get-city")
    fun getCity(
        @Query("start_id") start_id: String
    ): Call<ProvinceCityModel>

    @GET("api/get-faskes-vaksinasi")
    fun getFaskes(
        @Query("province") province: String,
        @Query("city") city : String
    ): Call<FaskesModel>

    @FormUrlEncoded
    @POST("check-in")
    fun postQrCode(
        @Field("qrCode") qrCode: String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude: Double
    ) : Call<QrCodeResponseModel>
}