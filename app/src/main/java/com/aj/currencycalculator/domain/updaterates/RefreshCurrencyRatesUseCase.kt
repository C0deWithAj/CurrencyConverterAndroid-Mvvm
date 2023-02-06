package com.aj.currencycalculator.domain.updaterates

import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import kotlinx.coroutines.flow.Flow

/**
 * Refresh Currency Rates from the Web
 *
 */
interface RefreshCurrencyRatesUseCase {
    fun refreshCurrencyRateFromAPI(): Flow<ResultData<List<CurrencyRateUI>>>
}
