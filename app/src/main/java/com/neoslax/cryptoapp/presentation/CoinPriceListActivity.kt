package com.neoslax.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.neoslax.cryptoapp.databinding.PriceListActivityBinding
import com.neoslax.cryptoapp.data.network.model.CoinInfoDto
import com.neoslax.cryptoapp.domain.entities.CoinInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PriceListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)

        binding.rvPriceListActivity.adapter = adapter
        binding.rvPriceListActivity.itemAnimator = null
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: CoinInfo) {
                Log.d("TEST_COIN_INFO", "Click on  ${coin.fromSymbol}")
                val intent =
                    CoinDetailActivity.getIntent(this@CoinPriceListActivity, coin.fromSymbol)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.coinInfoList.observe(this, {
            adapter.submitList(it)
        })

    }
}