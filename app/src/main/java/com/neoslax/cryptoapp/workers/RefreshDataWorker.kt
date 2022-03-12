package com.neoslax.cryptoapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.neoslax.cryptoapp.data.database.AppDatabase
import com.neoslax.cryptoapp.data.mapper.CoinMapper
import com.neoslax.cryptoapp.data.network.ApiFactory.apiService
import com.neoslax.cryptoapp.data.repository.CryptoAppRepositoryImpl
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private val coinMapper = CoinMapper()
    private val coinInfoDao = AppDatabase.getInstance(context).coinInfoDao()

    override suspend fun doWork(): Result {
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
        private const val RELOAD_DELAY_IN_MS = 1000L
        const val NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}