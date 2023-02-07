package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.toUIModel
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork
import com.aj.currencycalculator.data.network.model.toDBModel
import javax.inject.Inject

class ObjectMapperImp @Inject constructor() : ObjectMapper {

    override fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity> {
        return listCurrency.map {
            it.toDBModel()
        }
    }

    override fun entityToUI(listCurrencyEntity: List<CurrencyRateEntity>): List<CurrencyRateUI> {
        return listCurrencyEntity.map {
            it.toUIModel()
        }
    }
}
