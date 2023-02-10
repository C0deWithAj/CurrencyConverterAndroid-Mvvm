package com.aj.currencycalculator.domain.model

sealed class HistoricalData {
    class Date(var date: String?) : HistoricalData()
    class Currency(val code: String?, val rate: Double = 0.0) : HistoricalData()
}