package com.neoslax.cryptoapp.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    private val httpLogInterceptor = HttpLoggingInterceptor()

    private val retrofit = Retrofit.Builder()

        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(httpLogInterceptor).build())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)

}