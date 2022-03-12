package com.neoslax.cryptoapp.presentation

import android.app.Application
import com.neoslax.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}