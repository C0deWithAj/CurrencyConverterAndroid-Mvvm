package com.aj.currencycalculator.di

import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.mapper.LayersObjectMapper
import com.aj.currencycalculator.data.mapper.LayersObjectMapperImp
import com.aj.currencycalculator.data.network.CurrencyAPI
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.data.repository.CurrencyDataRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesDataRepository(
        currencyRateDAO: CurrencyRateDao,
        currencyTimeDao: CurrencyRateUpdateTimeDao,
        api: CurrencyAPI,
        networkDaoMapper: LayersObjectMapper
    ): CurrencyDataRepository =
        CurrencyDataRepositoryImp(currencyRateDAO, currencyTimeDao, api, networkDaoMapper)

    @Singleton
    @Provides
    fun providesNetworkMapper(): LayersObjectMapper = LayersObjectMapperImp()
}
