package com.aj.currencycalculator.util.extension

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.SearchHistoryUI

inline fun <T : Any> guardLet(vararg elements: T?, closure: () -> Nothing): List<T> {
    return if (elements.all { it != null }) {
        elements.filterNotNull()
    } else {
        closure()
    }
}

inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}

fun CurrencyRateEntity.toModel() = Currency(
    code = code, rate = rate
)

fun CurrencyRateNetwork.toDBModel() = CurrencyRateEntity(
    code = code ?: "", rate = rate
)

fun SearchHistoryEntity.toModel() = SearchHistoryUI.SearchHistory(
    dateTime = date,
    baseCurrency = "",
    toCurrency,
    rate = convertedValue
)

// TODO: Remove
fun SearchHistoryUI.SearchHistory.toEntityModel() = SearchHistoryEntity(
    date = dateTime,
    toCurrency = toCurrency,
    c = "",
    convertedValue = rate,
)

fun CurrencyRateNetwork.toModel() = HistoricalData.Currency(
    code = code,
    rate = rate
)
