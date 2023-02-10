package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.SearchHistoryUI

interface ObjectMapper {
    fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity>
    fun entityToModel(listCurrencyEntity: List<CurrencyRateEntity>): List<Currency>
    fun historyEntityToModel(searchHistoryEntity: List<SearchHistoryEntity>): List<SearchHistoryUI.SearchHistory>
    fun currencyHistoryEntityToModel(list: List<CurrencyRateNetwork>): List<HistoricalData.Currency>
}
