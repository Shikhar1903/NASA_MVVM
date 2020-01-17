package com.example.nasa_mvvm.model

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("apod")
    fun fetchPictureData(@Query("api_key") demoKey:String,@Query("date") date:String): Single<Items>
}