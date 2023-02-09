package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.data.model.ResultData
import java.util.*

interface CurrencyDataRepository {
    suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>>
    suspend fun getCurrencyRateList(): List<CurrencyRateEntity>
    suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity
    suspend fun getCurrencyRateList(currencyCode: String): List<CurrencyRateEntity>?
    suspend fun getHistoryForDate(from: Date, to: Date): List<SearchHistoryEntity>?
    suspend fun insertSearch(searchEntity: SearchHistoryEntity)
    suspend fun getCurrenciesRateList(currencyCodes: ArrayList<String>): List<CurrencyRateEntity>?
}
