package com.example.nasa_mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_mvvm.di.DaggerViewModelComponent
import com.example.nasa_mvvm.model.*
import javax.inject.Inject


class MainViewModel : ViewModel() {

    @Inject
    lateinit var mainModel: MainModel
    var items: MutableLiveData<roomItemsEntity> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getUrlFromModel(date: String, context: Context) {

        DaggerViewModelComponent.create().inject(this)
        mainModel.getDataOfDate(date, context)


    }
    @SuppressLint("CheckResult")
    fun getData(date:String){
        mainModel.roomDao.getUrlInfo(date).subscribeOn(SchedulersWrapper().io()).doOnSubscribe({
            
        })
            .subscribe({
                items.postValue(it)
            },{

            })
    }
}
















//        mainModel.getDataOfDate(date,context)!!.subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io())
//            .subscribe(
//            {
//
//
//                roomDao!!.setUrlInfo(roomItemsEntity1).subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io())
//                    .subscribe {
//                        d("check thread","Data is updated")
//                        roomDao!!.getUrlInfo(date).subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.io()).doOnSubscribe(
//                            {
//                                Log.d("Room Wuery", "Room query fetch started")
//                            }
//                        )
//                            .subscribe({ roomItemsEntity ->
//                                d("Am I running-","bhai kuch toh aao")
//                                items.postValue(roomItemsEntity)
//                            },{ throwable ->
//                                d("Room error", "$throwable")
//                            })
//                    }
//            },
//            {
//                //Add vpn if app crashes.
//                d("call error", "$it")
//            }
//        )