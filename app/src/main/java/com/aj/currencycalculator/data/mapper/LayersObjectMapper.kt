package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork

interface LayersObjectMapper {
    fun mapToDao(listCurrencyEntity: List<CurrencyRateNetwork>): List<CurrencyRateEntity>
    fun daoToUI(listCurrencyDao: List<CurrencyRateEntity>): List<CurrencyRateUI>
}
