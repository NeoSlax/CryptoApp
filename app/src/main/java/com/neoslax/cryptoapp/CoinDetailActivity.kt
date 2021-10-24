package com.neoslax.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        if (!intent.hasExtra(FROM_SYMBOL)) {
            finish()
            return
        }

        intent.getStringExtra(FROM_SYMBOL)?.let {
            viewModel.getCoinDetailInfo(it).observe(this) {
                Log.d("COIN_DETAIL_INFO", it.toString())
                with(binding) {
                    Picasso.get().load(it.getImageFullUrl()).into(ivDetailInfo)
                    tvFromCurrencyName.text = it.fromSymbol
                    tvToCurrencyName.text = it.toSymbol
                    tvCurrency.text = it.price.toString()
                    tvCurrencyMax.text = it.highDay.toString()
                    tvCurrencyMin.text = it.lowDay.toString()
                    tvLastTransaction.text = it.lastMarket
                    tvLastUpdate.text = it.getLastUpdateTime()

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
