package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository

class GetTopCoinPriceListUseCase(private val repository: CryptoAppRepository) {
    operator fun invoke() = repository.getTopCoinPriceList()
}