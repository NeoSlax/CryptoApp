package com.neoslax.cryptoapp.di

import android.app.Application
import com.neoslax.cryptoapp.data.repository.CryptoAppRepositoryImpl
import com.neoslax.cryptoapp.presentation.CoinApp
import com.neoslax.cryptoapp.presentation.CoinDetailFragment
import com.neoslax.cryptoapp.presentation.CoinPriceListFragment
import com.neoslax.cryptoapp.presentation.CoinViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DataModule::class,
        ViewModelModule::class,
        ApiModule::class,
        DomainModule::class,
        WorkerModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: CoinDetailFragment)

    fun inject(fragment: CoinPriceListFragment)

    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }


}