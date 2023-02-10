package com.aj.currencycalculator.domain.conversionhistory

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.model.SearchHistoryUI

interface SearchHistoryUseCase {
    suspend fun getHistoryForDays(lastDays: Int): ResultData<List<SearchHistoryUI.SearchHistory>?>
    suspend fun insert(searchHistoryUI: SearchHistoryUI.SearchHistory)
}
