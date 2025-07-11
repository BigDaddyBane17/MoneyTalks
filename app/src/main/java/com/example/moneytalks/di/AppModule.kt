package com.example.moneytalks.di

import com.example.data.di.AccountDataModule
import com.example.data.di.CategoryDataModule
import dagger.Module

@Module(
    includes = [
        AccountDataModule::class,
        CategoryDataModule::class
    ]
)
abstract class AppModule 