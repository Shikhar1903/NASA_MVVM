package com.example.nasa_mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_mvvm.di.DaggerViewModelComponent
import com.example.nasa_mvvm.model.*
import io.reactivex.Completable
import javax.inject.Inject


class MainViewModel : ViewModel() {

    @Inject lateinit var mainModel: MainModel
    var db: AppDatabase?=null
    var roomDao:roomItemsDao?=null
    private val schedulersWrapper = SchedulersWrapper()
    var items: MutableLiveData<roomItemsEntity> =MutableLiveData()

    @SuppressLint("CheckResult")
    fun getUrlFromModel(date:String,context:Context){

        DaggerViewModelComponent.create().inject(this)
        db= AppDatabase.getAppDataBase(context)
        var roomItemsEntity1:roomItemsEntity
        roomDao=db?.roomItemsDAO()
        mainModel.getDataOfDate(date)!!.subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io())
            .subscribe(
            {

                Log.d("check in view model", it!!.url)
                var checkImageURL= it!!.url
                if(checkImageURL.indexOf(".jpg")==-1)
                    checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"

 //             items.postValue(it)
                roomItemsEntity1= roomItemsEntity(room_url = it.url,room_title = it.title,
                    room_explanation = it.explanation,room_date = it.date,id=0)
                d("room table updation",""+roomItemsEntity1.room_url)
                roomDao!!.setUrlInfo(roomItemsEntity1).subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io())
                    .subscribe {
                        d("check thread","Data is updated")
                        roomDao!!.getUrlInfo(date).subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io()).doOnSubscribe(
                            {
                                Log.d("Room Wuery", "Room query fetch started")
                            }
                        )
                            .subscribe({ roomItemsEntity ->
                                d("Am I running-","bhai kuch toh aao")
                                items.postValue(roomItemsEntity)
                            },{ throwable ->
                                d("Room error", "$throwable")
                            })
                    }
            },
            {
                //Add vpn if app crashes.
                d("call error", "$it")
            }
        )
    }
}
