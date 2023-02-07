package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork

interface ObjectMapper {
    fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity>
    fun entityToUI(listCurrencyEntity: List<CurrencyRateEntity>): List<CurrencyRateUI>
}
