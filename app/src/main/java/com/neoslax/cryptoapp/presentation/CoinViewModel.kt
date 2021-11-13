package com.neoslax.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.neoslax.cryptoapp.data.repository.CryptoAppRepositoryImpl
import com.neoslax.cryptoapp.domain.usecases.GetCoinDetailInfoUseCase
import com.neoslax.cryptoapp.domain.usecases.GetTopCoinPriceListUseCase
import com.neoslax.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CryptoAppRepositoryImpl(application)
    private val getCoinDetailInfoUseCase = GetCoinDetailInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getTopCoinPriceListUseCase = GetTopCoinPriceListUseCase(repository)

    val coinInfoList = getTopCoinPriceListUseCase()

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

    fun getCoinDetailInfo(fSym: String) = getCoinDetailInfoUseCase(fSym)
}