package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.ui.model.CurrencyUI
import kotlinx.coroutines.flow.Flow

interface GetSavedCurrencyRateListUseCase {
    fun getSavedCurrencyList(): Flow<List<CurrencyUI>>
}
