package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.toUIModel
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork
import com.aj.currencycalculator.data.network.model.toDBModel
import javax.inject.Inject

class LayersObjectMapperImp @Inject constructor() : LayersObjectMapper {

    override fun mapToDao(listCurrencyEntity: List<CurrencyRateNetwork>): List<CurrencyRateEntity> {
        return listCurrencyEntity.map {
            it.toDBModel()
        }
    }

    override fun daoToUI(listCurrencyDao: List<CurrencyRateEntity>): List<CurrencyRateUI> {
        return listCurrencyDao.map {
            it.toUIModel()
        }
    }
}
