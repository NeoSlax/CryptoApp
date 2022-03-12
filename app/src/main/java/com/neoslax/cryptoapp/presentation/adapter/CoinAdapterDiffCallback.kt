package com.neoslax.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.neoslax.cryptoapp.domain.entities.CoinInfo

object CoinAdapterDiffCallback : DiffUtil.ItemCallback<CoinInfo>() {

    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}