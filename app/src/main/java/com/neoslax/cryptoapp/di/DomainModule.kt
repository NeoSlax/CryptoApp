package com.neoslax.cryptoapp.di

import com.neoslax.cryptoapp.data.repository.CryptoAppRepositoryImpl
import com.neoslax.cryptoapp.domain.repository.CryptoAppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: CryptoAppRepositoryImpl): CryptoAppRepository
}