package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import javax.inject.Inject

class GetTopCoinPriceListUseCase @Inject constructor(private val repository: CryptoAppRepository) {
    operator fun invoke() = repository.getTopCoinPriceList()
}