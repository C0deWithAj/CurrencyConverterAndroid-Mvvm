package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get Local Currency Rate list
 */
class GetSavedCurrencyRateListUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val layersObjectMapper: ObjectMapper
) : GetSavedCurrencyRateListUseCase {

    override fun getSavedCurrencyList(): Flow<List<CurrencyUI>> = flow {
        emit(layersObjectMapper.entityToUI(repository.getCurrencyRateList()))
    }
}
