package com.aj.currencycalculator.util.extension

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork
import com.aj.currencycalculator.ui.model.SearchHistoryUI

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

fun CurrencyRateEntity.toUIModel() = CurrencyUI(
    code = code, rate = rate
)

fun CurrencyRateNetwork.toDBModel() = CurrencyRateEntity(
    code = code ?: "", rate = rate
)

fun SearchHistoryEntity.toUIModel() = SearchHistoryUI.SearchHistory(
    dateTime = date,
    baseCurrency = baseCurrency,
    toCurrency,
    rate = convertedValue
)

fun SearchHistoryUI.SearchHistory.toEntityModel() = SearchHistoryEntity(
    date = dateTime,
    baseCurrency = baseCurrency,
    toCurrency = toCurrency,
    convertedValue = rate
)
