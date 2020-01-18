package com.example.nasa_mvvm.model

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainModel {

    private var mRetrofit: Retrofit? = null

    fun getDataOfDate(date: String): Single<Items?>? {
        return getRetrofit()?.create(ApiService::class.java)
            ?.fetchPictureData("DEMO_KEY",date)
    }

    private fun getRetrofit(): Retrofit? {
        if (mRetrofit == null) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            mRetrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build()
        }
        return mRetrofit
    }
}
