package com.neoslax.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.neoslax.cryptoapp.domain.entities.CoinInfo

interface CryptoAppRepository {

    fun getCoinDetailInfo(coinName: String): LiveData<CoinInfo>

    fun getTopCoinPriceList(): LiveData<List<CoinInfo>>

    fun loadData()

}