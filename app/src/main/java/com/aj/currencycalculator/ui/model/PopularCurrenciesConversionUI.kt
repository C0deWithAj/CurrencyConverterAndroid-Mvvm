package com.aj.currencycalculator.ui.model

data class PopularCurrenciesConversionUI(
    val baseCurrency: CurrencyUI,
    val convertedCurrencies: List<ConvertedConversionUI>
)
