package com.aj.currencycalculator.domain.updaterates

import com.aj.currencycalculator.data.mapper.LayersObjectMapper
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshCurrencyRatesUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val layersObjectMapper: LayersObjectMapper
) : RefreshCurrencyRatesUseCase {

    override fun refreshCurrencyRateFromAPI(): Flow<ResultData<List<CurrencyRateUI>>> = flow {
        emit(ResultData.Loading())
        when (val result = repository.updateDataFromNetwork()) {
            is ResultData.Success -> {
                result.data?.let {
                    emit(ResultData.Success(layersObjectMapper.entityToUI(result.data)))
                }
            }

            is ResultData.Failed -> {
                emit(result)
            }
            is ResultData.Exception -> {
                result.exception?.printStackTrace()
                emit(result)
            }
            else -> {
                emit(ResultData.Failed("Unknown Error occurred"))
            }
        }
    }
}
