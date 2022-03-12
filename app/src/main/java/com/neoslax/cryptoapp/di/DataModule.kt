package com.neoslax.cryptoapp.di

import android.app.Application
import android.content.Context
import com.neoslax.cryptoapp.data.database.AppDatabase
import com.neoslax.cryptoapp.data.database.CoinInfoDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCoinInfoDao(application: Application): CoinInfoDao {
        return AppDatabase.getInstance(application).coinInfoDao()
    }
}