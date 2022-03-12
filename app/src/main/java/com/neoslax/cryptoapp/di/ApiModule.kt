package com.neoslax.cryptoapp.di

import com.neoslax.cryptoapp.data.network.ApiFactory
import com.neoslax.cryptoapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ApiModule {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

}