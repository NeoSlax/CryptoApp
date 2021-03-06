package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import javax.inject.Inject

class GetCoinDetailInfoUseCase @Inject constructor(private val repository: CryptoAppRepository) {
    operator fun invoke(coinName: String) = repository.getCoinDetailInfo(coinName)
}