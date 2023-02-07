package com.aj.currencycalculator.domain.currrencyconverter

import android.util.Log
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.util.extension.removeDotConvertToDouble
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CurrencyConverterUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository
) : CurrencyConverterUseCase {

    override fun calculateCurrency(
        input: String,
        baseCurrency: String, targetCurrency: String

    ): Flow<ResultData<Double>> = flow {
        emit(ResultData.Loading())
        val baseCurrencyList = repository.getCurrencyRateList(baseCurrency)
        val targetCurrencyList = repository.getCurrencyRateList(targetCurrency)
        try {
            if (!baseCurrencyList.isNullOrEmpty() && !targetCurrencyList.isNullOrEmpty()) {
                val rate = targetCurrencyList.first().rate / baseCurrencyList.first().rate
                val inputVal = input.removeDotConvertToDouble()
                inputVal?.let {
                    emit(ResultData.Success(it * rate))
                }
            } else {
                emit(ResultData.Failed("Error in Fetching conversion rates - Please try Again"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(ResultData.Failed("An Error occurred in calculating currency conversion"))
        }
    }.flowOn(Dispatchers.IO)

}