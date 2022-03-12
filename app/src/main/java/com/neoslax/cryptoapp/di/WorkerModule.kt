package com.neoslax.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.neoslax.cryptoapp.presentation.CoinViewModel
import com.neoslax.cryptoapp.workers.ChildWorkerFactory
import com.neoslax.cryptoapp.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorker(worker: RefreshDataWorker.Factory): ChildWorkerFactory
}