package com.aj.currencycalculator.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean = false,
    @SerializedName("timestamp")
    @Expose
    val timestamp: Long = 0L,
    @SerializedName("base")
    @Expose
    val base: String?,
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("rates")
    @Expose
    val rates: Rates?,
    @SerializedName("error")
    @Expose
    val error: CurrencyAPIError?
)
