package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork
import com.aj.currencycalculator.ui.model.SearchHistoryUI

interface ObjectMapper {
    fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity>
    fun entityToUI(listCurrencyEntity: List<CurrencyRateEntity>): List<CurrencyUI>
    fun historyEntityToUI(searchHistoryEntity: List<SearchHistoryEntity>): List<SearchHistoryUI.SearchHistory>
}
