package com.neoslax.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.neoslax.cryptoapp.R
import com.neoslax.cryptoapp.databinding.ItemPriceInfoBinding
import com.neoslax.cryptoapp.domain.entities.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinAdapterDiffCallback) {

    var onCoinClickListener: OnCoinClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemPriceInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val timePattern = context.resources.getString(R.string.last_update_txt)
        val currencyPattern = context.resources.getString(R.string.currency_pattern)
        val coinPriceInfo = getItem(position)
        with(holder.binding) {
            tvCryptoCurrency.text = String.format(
                currencyPattern,
                coinPriceInfo.fromSymbol,
                coinPriceInfo.toSymbol
            )
            tvLastUpdate.text = String.format(timePattern, coinPriceInfo.lastUpdate)
            tvCurrencyVal.text = coinPriceInfo.price.cropPriceValue()
            Picasso.get().load(coinPriceInfo.imageUrl)
                .placeholder(R.drawable.progress_animation)
                .into(ivCurrencyImage)
            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coinPriceInfo)
            }
        }

    }

    private fun String?.cropPriceValue(): String {
        return this?.let { String.format("%.2f", this.toDouble()) } ?: ""
    }

    interface OnCoinClickListener {
        fun onCoinClick(coin: CoinInfo)
    }
}