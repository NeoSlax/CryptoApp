package com.neoslax.cryptoapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.neoslax.cryptoapp.data.database.AppDatabase
import com.neoslax.cryptoapp.data.database.CoinInfoDao
import com.neoslax.cryptoapp.data.mapper.CoinMapper
import com.neoslax.cryptoapp.data.network.ApiFactory
import com.neoslax.cryptoapp.data.network.ApiService
import com.neoslax.cryptoapp.domain.entities.CoinInfo
import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CryptoAppRepositoryImpl @Inject constructor(
    application: Application,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val coinMapper: CoinMapper
) : CryptoAppRepository {

    override fun getCoinDetailInfo(coinName: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(coinName)) {
                value = coinMapper.mapDbModelToEntity(it)
            }
        }

    }

    override fun getTopCoinPriceList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                coinMapper.mapDbModelToEntity(it)
            }
        }

    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinInfo(limit = 50)
                val fSyms = coinMapper.mapCoinNamesListToString(topCoins)
                val coinInfoJson = apiService.getPriceInfoFullData(fSym = fSyms)
                val coinInfoDtoList = coinMapper.mapJsonContainerToListCoinInfo(coinInfoJson)
                coinInfoDao.insertPriceList(coinInfoDtoList.map { coinMapper.mapDtoToDbModel(it) })
                Log.d("TEST", "loadData()  == $coinInfoDtoList")
            } catch (e: Exception) {
            }
            delay(RELOAD_DELAY_IN_MS)
        }
    }

    companion object {
        private const val RELOAD_DELAY_IN_MS = 10_000L
    }
}