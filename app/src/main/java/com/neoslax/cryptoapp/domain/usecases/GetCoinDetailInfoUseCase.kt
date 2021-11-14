package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository

class GetCoinDetailInfoUseCase(private val repository: CryptoAppRepository) {
    operator fun invoke(coinName: String) = repository.getCoinDetailInfo(coinName)
}