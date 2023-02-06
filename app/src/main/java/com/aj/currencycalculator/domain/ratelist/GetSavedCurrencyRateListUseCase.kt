package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.data.model.CurrencyRateUI
import kotlinx.coroutines.flow.Flow

interface GetSavedCurrencyRateListUseCase {
    fun getSavedCurrencyList(): Flow<List<CurrencyRateUI>>
}
