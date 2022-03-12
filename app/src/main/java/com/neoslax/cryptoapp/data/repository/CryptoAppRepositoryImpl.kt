package com.neoslax.cryptoapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.neoslax.cryptoapp.data.database.AppDatabase
import com.neoslax.cryptoapp.data.database.CoinInfoDao
import com.neoslax.cryptoapp.data.mapper.CoinMapper
import com.neoslax.cryptoapp.data.network.ApiFactory
import com.neoslax.cryptoapp.data.network.ApiService
import com.neoslax.cryptoapp.domain.entities.CoinInfo
import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import com.neoslax.cryptoapp.workers.RefreshDataWorker
import kotlinx.coroutines.delay
import javax.inject.Inject

class CryptoAppRepositoryImpl @Inject constructor(
    private val application: Application,
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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

    companion object {
        private const val RELOAD_DELAY_IN_MS = 10_000L
    }
}