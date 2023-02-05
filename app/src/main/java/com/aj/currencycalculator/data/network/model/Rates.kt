package com.aj.currencycalculator.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rates {
    @SerializedName("base")
    @Expose
    var conversionRates = listOf<CurrencyRateNetwork>()
}