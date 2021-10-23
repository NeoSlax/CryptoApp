package com.neoslax.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neoslax.cryptoapp.R
import com.neoslax.cryptoapp.databinding.ItemPriceInfoBinding
import com.neoslax.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null

    var coinList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        val coinPriceInfo = coinList[position]
        with(holder.binding) {
            tvCryptoCurrency.text = String.format(
                currencyPattern,
                coinPriceInfo.fromSymbol,
                coinPriceInfo.toSymbol
            )
            tvLastUpdate.text = String.format(timePattern, coinPriceInfo.getLastUpdateTime())
            tvCurrencyVal.text = coinPriceInfo.price.toString()
            Picasso.get().load(coinPriceInfo.getImageFullUrl()).into(ivCurrencyImage)
            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coinPriceInfo)
            }
        }

    }

    override fun getItemCount(): Int = coinList.size


    inner class CoinInfoViewHolder(val binding: ItemPriceInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coin: CoinPriceInfo)
    }
}