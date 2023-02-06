package com.aj.currencycalculator.di

import com.aj.currencycalculator.data.mapper.LayersObjectMapper
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.FetchCurrencyRateUseCase
import com.aj.currencycalculator.domain.FetchCurrencyRateUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesFetchCurrencyRateUseCase(
        repository: CurrencyDataRepository,
        mapper: LayersObjectMapper
    ): FetchCurrencyRateUseCase =
        FetchCurrencyRateUseCaseImp(repository, mapper)
}
