package com.aj.currencycalculator.domain.currencyhistory

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.SearchHistoryUI
import com.aj.currencycalculator.util.DateTimeUtil
import com.aj.currencycalculator.util.extension.translateToError
import javax.inject.Inject


class CurrencyHistoryUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val dataMapper: ObjectMapper
) : CurrencyHistoryUseCase {

    override suspend fun getHistoryForDays(lastDays: Int): ResultData<List<HistoricalData>?> {
        val result = arrayListOf<HistoricalData>()
        try {
            val yesterday = DateTimeUtil.getMillisOfLastXDays(1)
            val pastDate = DateTimeUtil.getMillisOfLastXDays(lastDays)
            if (yesterday != null && pastDate != null) {
                val hash = repository.getHistoricalData(pastDate, yesterday)
                if (!hash.isNullOrEmpty()) {
                    for ((key, list) in hash) {
                        val date = HistoricalData.Date(key)
                        result.add(date)
                        if (!list.isNullOrEmpty()) {
                            val listOfCurrency = dataMapper.currencyHistoryEntityToModel(list)
                            result.addAll(listOfCurrency)
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ex.translateToError()
        }
        return ResultData.Success(result)
    }

    override suspend fun insert(search: String) {
        repository.insertCurrencySearch(search)
    }
}