package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.model.ResultData

interface CurrencyDataRepository {
    suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>>
    suspend fun getCurrencyRateList(): List<CurrencyRateEntity>
    suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity
}
