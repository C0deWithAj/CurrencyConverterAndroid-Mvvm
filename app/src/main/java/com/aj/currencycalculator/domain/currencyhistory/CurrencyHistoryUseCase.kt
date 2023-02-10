package com.aj.currencycalculator.domain.currencyhistory

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.SearchHistoryUI

interface CurrencyHistoryUseCase {
    suspend fun getHistoryForDays(lastDays: Int): ResultData<List<HistoricalData>?>
    suspend fun insert(currencyCode: String)
}