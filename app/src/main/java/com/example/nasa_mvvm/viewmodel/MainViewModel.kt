package com.example.nasa_mvvm.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_mvvm.model.Items
import com.example.nasa_mvvm.model.MainModel


class MainViewModel:ViewModel() {

    private lateinit var mainModel: MainModel
    private val schedulersWrapper = SchedulersWrapper()


    @SuppressLint("CheckResult")
    fun getUrlFromModel(date:String):MutableLiveData<Items> {

        var items: MutableLiveData<Items> =MutableLiveData()
        mainModel=MainModel()
        mainModel.getDataOfDate(date)!!.subscribeOn(schedulersWrapper.io()).subscribe(
            {

                Log.d("check in view model", it.url)
                var checkImageURL=it.url
                if(checkImageURL.indexOf(".jpg")==-1)
                    checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"

                items.postValue(it)


            },
            {

            }
        )
        return items
    }
}
