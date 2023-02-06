package com.aj.currencycalculator.data.network.model

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyRateNetwork(
    @SerializedName("code")
    @Expose
    val code: String?,
    @SerializedName("rate")
    @Expose val rate: Double = 0.0
)

fun CurrencyRateNetwork.toDBModel() = CurrencyRateEntity(
    code = code ?: "", rate = rate
)
