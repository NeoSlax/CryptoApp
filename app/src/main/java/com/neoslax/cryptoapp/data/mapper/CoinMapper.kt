package com.neoslax.cryptoapp.data.mapper

import com.google.gson.Gson
import com.neoslax.cryptoapp.data.database.CoinInfoDbModel
import com.neoslax.cryptoapp.data.network.model.CoinInfoDto
import com.neoslax.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.neoslax.cryptoapp.data.network.model.CoinNamesListDto
import com.neoslax.cryptoapp.domain.entities.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMG_URL + dto.imageUrl
    )

    fun mapDbModelToEntity(db: CoinInfoDbModel) = CoinInfo(
        fromSymbol = db.fromSymbol,
        toSymbol = db.toSymbol,
        price = db.price,
        lastUpdate = timeStampConverter(db.lastUpdate),
        highDay = db.highDay,
        lowDay = db.lowDay,
        lastMarket = db.lastMarket,
        imageUrl = db.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val resultList = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.jsonObject
        val cryptoCurrencyKeySet =
            jsonObject?.keySet() ?: return resultList

        for (cryptoCurrencyKey in cryptoCurrencyKeySet) {
            val jsonObjectCurrency = jsonObject.getAsJsonObject(
                cryptoCurrencyKey
            )
            val currencyKeySet = jsonObjectCurrency.keySet()
            for (currencyKey in currencyKeySet) {
                val element = jsonObjectCurrency.getAsJsonObject(currencyKey)
                val coinPriceInfo = Gson().fromJson(element, CoinInfoDto::class.java)
                resultList.add(coinPriceInfo)
            }

        }

        return resultList
    }

    fun mapCoinNamesListToString(coinNamesListDto: CoinNamesListDto): String {
        return coinNamesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: "nulls"
    }

    private fun timeStampConverter(timestamp: Long?): String{

        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()

        }
        return simpleDateFormat.format(date)

    }

    companion object {
        private const val BASE_IMG_URL = "https://cryptocompare.com"
    }
}