package com.neoslax.cryptoapp.api

import com.neoslax.cryptoapp.pojo.CoinInfoListOfData
import com.neoslax.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TOSYM) tSym: String = CURRENCY
        ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getPriceInfoFullData(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROMSYM) fSym: String = FROMCURRENCY,
        @Query(QUERY_PARAM_TOSYMS) tSym: String = CURRENCY
        ): Single<CoinPriceInfoRawData>

    companion object{
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TOSYM = "tsym"
        private const val QUERY_PARAM_TOSYMS = "tsyms"
        private const val QUERY_PARAM_FROMSYM = "fsyms"

        private const val CURRENCY = "USD"
        private const val FROMCURRENCY = "BTC"
    }
}