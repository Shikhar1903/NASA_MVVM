package com.example.nasa_mvvm.di

import com.example.nasa_mvvm.viewmodel.MainViewModel
import dagger.Component
import dagger.Module

@Component(modules=[ViewModelModule::class])
interface ViewModelComponent {

    fun inject(app:MainViewModel)
}