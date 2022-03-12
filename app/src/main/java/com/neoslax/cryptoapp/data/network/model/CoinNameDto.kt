package com.neoslax.cryptoapp.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinNameDto(
    @SerializedName("Id")
    @Expose
    val id: String? = null,

    @SerializedName("Name")
    @Expose
    val name: String? = null,

    @SerializedName("FullName")
    @Expose
    val fullName: String? = null,

    @SerializedName("Internal")
    @Expose
    val internal: String? = null,

    @SerializedName("ImageUrl")
    @Expose
    val imageUrl: String? = null,

    @SerializedName("Url")
    @Expose
    val url: String? = null
)

