package com.neoslax.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(FROM_SYMBOL)) {
            finish()
            return
        }

        intent.getStringExtra(FROM_SYMBOL)?.let {
            viewModel.getCoinDetailInfo(it).observe(this) {
                Log.d("COIN_DETAIL_INFO", it.toString())
                with(binding) {
                    Picasso.get().load(it.imageUrl).into(ivDetailInfo)
                    tvFromCurrencyName.text = it.fromSymbol
                    tvToCurrencyName.text = it.toSymbol
                    tvCurrency.text = it.price.toString()
                    tvCurrencyMax.text = it.highDay.toString()
                    tvCurrencyMin.text = it.lowDay.toString()
                    tvLastTransaction.text = it.lastMarket
                    tvLastUpdate.text = it.lastUpdate

                }
            }
        }


    }

    companion object {
        private const val FROM_SYMBOL = "fSym"

        fun getIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
