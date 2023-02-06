package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.data.mapper.LayersObjectMapper
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get Local Currency Rate list
 */
class GetSavedCurrencyRateListUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val layersObjectMapper: LayersObjectMapper
) : GetSavedCurrencyRateListUseCase {

    override fun getSavedCurrencyList(): Flow<List<CurrencyRateUI>> = flow {
        emit(layersObjectMapper.entityToUI(repository.getCurrencyRateList()))
    }
}
