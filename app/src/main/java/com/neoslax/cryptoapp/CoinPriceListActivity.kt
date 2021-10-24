package com.neoslax.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.adapter.CoinInfoAdapter
import com.neoslax.cryptoapp.databinding.PriceListActivityBinding
import com.neoslax.cryptoapp.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PriceListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)

        binding.rvPriceListActivity.adapter = adapter
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: CoinPriceInfo) {
                Log.d("TEST_COIN_INFO", "Click on  ${coin.fromSymbol}")
                val intent =
                    CoinDetailActivity.getIntent(this@CoinPriceListActivity, coin.fromSymbol)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinDetailInfo("BTC").observe(this, {
            Log.d("TEST_COIN_INFO", "Success in activity ${it.toString()}")

        })

        viewModel.priceList.observe(this, {
            adapter.coinList = it
        })

    }
}