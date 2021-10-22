package com.neoslax.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.neoslax.cryptoapp.api.ApiFactory
import com.neoslax.cryptoapp.database.AppDatabase
import com.neoslax.cryptoapp.pojo.CoinPriceInfo
import com.neoslax.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val apiService = ApiFactory.apiService
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()


    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "nulls" }
            .flatMap { ApiFactory.apiService.getPriceInfoFullData(fSym = it) }
            .map { getCoinInfoFromJson(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_COIN_INFO", "Success download in loadData() with result: ${it.toString()}")
            }, {
                Log.d("TEST_COIN_INFO", "Failure loadData() with: ${it.toString()}")
            })
        compositeDisposable.add(disposable)
    }

    fun getCoinInfoFromJson(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val resultList = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        val cryptoCurrencyKeySet =
            jsonObject?.keySet() ?: return resultList

        for (cryptoCurrencyKey in cryptoCurrencyKeySet) {
            val jsonObjectCurrency = jsonObject.getAsJsonObject(
                cryptoCurrencyKey
            )
            val currencyKeySet = jsonObjectCurrency.keySet()
            for (currencyKey in currencyKeySet) {
                val element = jsonObjectCurrency.getAsJsonObject(currencyKey)
                val coinPriceInfo = Gson().fromJson(element, CoinPriceInfo::class.java)
                resultList.add(coinPriceInfo)
            }

        }

        return resultList
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}