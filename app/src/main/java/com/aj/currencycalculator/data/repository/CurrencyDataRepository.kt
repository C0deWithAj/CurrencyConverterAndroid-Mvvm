package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.model.ResultData

interface CurrencyDataRepository {
    suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>>
}
