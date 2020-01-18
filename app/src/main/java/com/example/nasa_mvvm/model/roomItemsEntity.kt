package com.example.nasa_mvvm.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class roomItemsEntity(

    @PrimaryKey(autoGenerate = true) val id:Int,
    val room_url:String,
    val room_explanation:String,
    val room_title:String,
    val room_date:String
)