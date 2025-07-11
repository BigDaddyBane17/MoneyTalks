package com.example.moneytalks.di

import com.example.data.di.AccountDataModule
import dagger.Module

@Module(
    includes = [
        AccountDataModule::class
    ]
)
abstract class AppModule 