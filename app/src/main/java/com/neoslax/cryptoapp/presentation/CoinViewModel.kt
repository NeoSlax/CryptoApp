package com.neoslax.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neoslax.cryptoapp.domain.usecases.GetCoinDetailInfoUseCase
import com.neoslax.cryptoapp.domain.usecases.GetTopCoinPriceListUseCase
import com.neoslax.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinDetailInfoUseCase: GetCoinDetailInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val getTopCoinPriceListUseCase: GetTopCoinPriceListUseCase
) : ViewModel() {

    val coinInfoList = getTopCoinPriceListUseCase()

    init {

        viewModelScope.launch {
            loadDataUseCase()
        }
    }

    fun getCoinDetailInfo(fSym: String) = getCoinDetailInfoUseCase(fSym)
}