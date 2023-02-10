package com.aj.currencycalculator.domain.conversionhistory

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.SearchHistoryUI
import com.aj.currencycalculator.util.DateTimeUtil
import com.aj.currencycalculator.util.extension.toEntityModel
import com.aj.currencycalculator.util.extension.translateToError
import javax.inject.Inject

class SearchHistoryUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val dataMapper: ObjectMapper
) : SearchHistoryUseCase {

    override suspend fun getHistoryForDays(lastDays: Int): ResultData<List<SearchHistoryUI.SearchHistory>?> {
        try {
            val yesterday = DateTimeUtil.getMillisOfLastXDays(1)
            val pastDate = DateTimeUtil.getMillisOfLastXDays(lastDays) // 5 days ago
            if (yesterday != null && pastDate != null) {
                val result = repository.getHistoryForDate(pastDate, yesterday)
                result?.let {
                    return ResultData.Success(dataMapper.historyEntityToModel(it))
                } ?: run {
                    return ResultData.Success(listOf())
                }
            }
            return ResultData.Success(listOf())
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ex.translateToError()
        }
    }

    override suspend fun insert(searchHistoryUI: SearchHistoryUI.SearchHistory) {
        repository.insertSearch(searchHistoryUI.toEntityModel())
    }
}
