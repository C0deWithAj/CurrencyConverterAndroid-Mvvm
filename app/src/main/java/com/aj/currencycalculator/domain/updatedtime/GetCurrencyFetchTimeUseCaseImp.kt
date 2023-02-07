package com.aj.currencycalculator.domain.updatedtime

import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.util.extension.toDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrencyFetchTimeUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository
) : GetCurrencyFetchTimeUseCase {
    override fun getLastUpdateTime(): Flow<String?> = flow {
        val dateTime = repository.getCurrencyUpdateTime()
        emit(dateTime.updatedAt.toDateTime())
    }.flowOn(Dispatchers.IO)
}
