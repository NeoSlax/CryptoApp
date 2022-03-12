package com.neoslax.cryptoapp.domain.usecases

import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CryptoAppRepository) {

    operator fun invoke() = repository.loadData()
}