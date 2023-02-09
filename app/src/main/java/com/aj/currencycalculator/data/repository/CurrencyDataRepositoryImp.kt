package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.db.dao.SearchHistoryDao
import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.network.CurrencyAPI
import com.aj.currencycalculator.data.network.model.toListOfRates
import com.aj.currencycalculator.util.extension.translateToError
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local Database is ultimate source of truth
 */

@Singleton
class CurrencyDataRepositoryImp @Inject constructor(
    private val currencyRateDao: CurrencyRateDao,
    private val currencyTimeDao: CurrencyRateUpdateTimeDao,
    private val searchHistoryDao: SearchHistoryDao,
    private val currencyConverterAPI: CurrencyAPI,
    private val networkDaoMapper: ObjectMapper
) : CurrencyDataRepository {

    override suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>> {
        try {
            val response = currencyConverterAPI.getCurrencies()
            if (response.success) {
                val result = response.toListOfRates()
                result?.let {
                    val daoRates = networkDaoMapper.mapToEntity(it)
                    currencyTimeDao.insert(
                        CurrencyRateUpdateTimeEntity(
                            "1",
                            response.timestamp
                        )
                    )
                    currencyRateDao.insert(daoRates)
                    return ResultData.Success(daoRates)
                } ?: run {
                    return ResultData.Failed(
                        title = "Oh Snap!",
                        message = "Server seems busy - Please try again later"
                    )
                }
            } else {
                return ResultData.Failed(
                    title = "API Error",
                    message = "Error code ${response.error?.code ?: " Reason - ${response.error}"},"
                )
            }
        } catch (ex: Exception) {
            return ex.translateToError()
        }
    }

    override suspend fun getHistoryForDate(from: Date, to: Date): List<SearchHistoryEntity>? =
        searchHistoryDao.getHistoryForDate(from, to)

    override suspend fun insertSearch(searchEntity: SearchHistoryEntity) {
        searchHistoryDao.insert(searchEntity)
    }

    override suspend fun getCurrencyRateList(): List<CurrencyRateEntity> =
        currencyRateDao.getCurrencyList()

    override suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity =
        currencyTimeDao.findLastTimeStamp()

    override suspend fun getCurrencyRateList(currencyCode: String): List<CurrencyRateEntity>? =
        currencyRateDao.getCurrencyRate(currencyCode)

    override suspend fun getCurrenciesRateList(currencyCodes: ArrayList<String>): List<CurrencyRateEntity>? =
        currencyRateDao.getCurrencyRate(currencyCodes)



}
