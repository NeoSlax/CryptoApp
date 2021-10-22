package com.neoslax.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinDetailInfo("BTC").observe(this, Observer {
            Log.d("TEST_COIN_INFO", "Success in activity ${it.toString()}")
        })

    }
}