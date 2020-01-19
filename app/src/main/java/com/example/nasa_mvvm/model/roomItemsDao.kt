package com.example.nasa_mvvm.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface roomItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUrlInfo(roomItemEntity: roomItemsEntity): Completable

    @Query("SELECT * FROM roomItemsEntity WHERE room_date== :date")
    fun getUrlInfo(date:String): Flowable<roomItemsEntity>
}