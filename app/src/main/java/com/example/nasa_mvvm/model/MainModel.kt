package com.example.nasa_mvvm.model

import android.util.Log
import io.reactivex.Single
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
            mRetrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }
        return mRetrofit
    }
}
