package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository

class LoadDataUseCase(private val repository: CryptoAppRepository) {

    suspend operator fun invoke() = repository.loadData()
}