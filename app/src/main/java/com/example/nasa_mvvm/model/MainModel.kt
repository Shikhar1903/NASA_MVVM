package com.example.nasa_mvvm.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.Log.d
import com.example.nasa_mvvm.viewmodel.SchedulersWrapper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class MainModel @Inject constructor() {

    private var mRetrofit: Retrofit? = null
    var db: AppDatabase?=null
    lateinit var roomDao:roomItemsDao
    private val schedulersWrapper = SchedulersWrapper()

    @SuppressLint("CheckResult")
    fun getDataOfDate(date: String, context:Context) {

        db= AppDatabase.getAppDataBase(context)
        roomDao=db!!.roomItemsDAO()
        var roomItemsEntity1:roomItemsEntity

        getRetrofit()?.create(ApiService::class.java)
            ?.fetchPictureData("DEMO_KEY",date)!!.subscribeOn(schedulersWrapper.io())
            .subscribe({
                Log.d("check in model", it!!.url)
                var checkImageURL= it.url
                if(checkImageURL.indexOf(".jpg")==-1)
                    checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"

                roomItemsEntity1= roomItemsEntity(room_url = it.url,room_title = it.title,
                    room_explanation = it.explanation,room_date = it.date)
                roomDao.setUrlInfo(roomItemsEntity1).observeOn(schedulersWrapper.io())
                    .subscribe {
                        Log.d("room table updation", "room was successfully updated")
                    }
            },{
                d("retrofit error","check internet connection")
            })
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