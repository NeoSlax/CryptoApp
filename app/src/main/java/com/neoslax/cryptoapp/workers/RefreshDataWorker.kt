package com.neoslax.cryptoapp.workers

import android.content.Context
import android.util.Log
import androidx.work.*
import com.neoslax.cryptoapp.data.database.AppDatabase
import com.neoslax.cryptoapp.data.database.CoinInfoDao
import com.neoslax.cryptoapp.data.mapper.CoinMapper
import com.neoslax.cryptoapp.data.network.ApiService

import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val coinMapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParams) {


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

    class Factory @Inject constructor(
        private val coinMapper: CoinMapper,
        private val coinInfoDao: CoinInfoDao,
        private val apiService: ApiService
    ) : ChildWorkerFactory {
        override fun create(context: Context, workerParams: WorkerParameters): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParams,
                coinMapper,
                coinInfoDao,
                apiService
            )
        }
    }
}