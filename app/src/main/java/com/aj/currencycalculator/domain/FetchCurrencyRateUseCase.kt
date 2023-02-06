package com.aj.currencycalculator.domain

import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import kotlinx.coroutines.flow.Flow

interface FetchCurrencyRateUseCase {
    fun refreshCurrencyRateFromAPI(): Flow<ResultData<List<CurrencyRateUI>>>

}