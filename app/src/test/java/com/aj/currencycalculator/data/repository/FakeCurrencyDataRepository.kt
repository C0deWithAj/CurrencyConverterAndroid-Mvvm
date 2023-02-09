package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.data.model.ResultData
import java.util.*

class FakeCurrencyDataRepository : CurrencyDataRepository {

    private var shouldReturnNetworkError = false
    private var currencyRateList: List<CurrencyRateEntity>? = null

    fun setShouldReturnNetworkError(networkError: Boolean) {
        this.shouldReturnNetworkError = networkError
    }

    fun setCurrencyRateList(rateList: List<CurrencyRateEntity>) {
        this.currencyRateList = rateList
    }


    override suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>> {
        return if (shouldReturnNetworkError) {
            ResultData.Exception()
        } else {
            ResultData.Success(currencyRateList)
        }
    }

    override suspend fun getCurrencyRateList(): List<CurrencyRateEntity> {
        return listOf()
    }

    override suspend fun getCurrencyRateList(currencyCode: String): List<CurrencyRateEntity>? {
        return null
    }

    override suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity {
        return CurrencyRateUpdateTimeEntity(
            "1",
            System.currentTimeMillis(),
            System.currentTimeMillis()
        )
    }

    override suspend fun getHistoryForDate(from: Date, to: Date): List<SearchHistoryEntity>? {
        return null
    }

}