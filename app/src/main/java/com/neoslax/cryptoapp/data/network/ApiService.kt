package com.neoslax.cryptoapp.data.network

import com.neoslax.cryptoapp.data.network.model.CoinNamesListDto
import com.neoslax.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TOSYM) tSym: String = CURRENCY
        ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getPriceInfoFullData(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROMSYM) fSym: String = FROMCURRENCY,
        @Query(QUERY_PARAM_TOSYMS) tSym: String = CURRENCY
        ): CoinInfoJsonContainerDto

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