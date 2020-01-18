package com.example.nasa_mvvm.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_mvvm.model.Items
import com.example.nasa_mvvm.model.MainModel
import io.reactivex.subjects.PublishSubject


class MainViewModel():ViewModel() {

    private lateinit var mainModel: MainModel
    private val schedulersWrapper = SchedulersWrapper()
    lateinit var itemObservable: PublishSubject<Items>
    var items: MutableLiveData<Items> =MutableLiveData()

    constructor(mMainModel:MainModel):this(){
        mainModel=mMainModel
        itemObservable = PublishSubject.create()
    }

    @SuppressLint("CheckResult")
    fun getUrlFromModel(date:String):MutableLiveData<Items> {

        mainModel.getDataOfDate(date)!!.subscribeOn(schedulersWrapper.io()).subscribe(
            {
                Log.d("check in view model", it.url)
                items.value?.url = it.url
                items.value?.date = it.date
                items.value?.explanation = it.explanation
                items.value?.title = it.title

            },
            {

            }
        )
        return items
    }
}
