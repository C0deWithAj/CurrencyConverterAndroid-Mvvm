package com.aj.currencycalculator.di

import com.aj.currencycalculator.data.mapper.LayersObjectMapper
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCase
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCaseImp
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCase
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCaseImp
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCase
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCaseImp
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
    ): RefreshCurrencyRatesUseCase =
        RefreshCurrencyRatesUseCaseImp(repository, mapper)

    @Singleton
    @Provides
    fun providesSavedCurrencyRateUseCase(
        repository: CurrencyDataRepository,
        mapper: LayersObjectMapper
    ): GetSavedCurrencyRateListUseCase =
        GetSavedCurrencyRateListUseCaseImp(repository, mapper)

    @Singleton
    @Provides
    fun provideLastFetchDateUseCase(
        repository: CurrencyDataRepository
    ): GetCurrencyFetchTimeUseCase =
        GetCurrencyFetchTimeUseCaseImp(repository)
}
