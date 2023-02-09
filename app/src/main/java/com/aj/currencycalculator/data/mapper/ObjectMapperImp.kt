package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.data.network.model.CurrencyRateNetwork
import com.aj.currencycalculator.ui.model.SearchHistoryUI
import com.aj.currencycalculator.util.extension.toDBModel
import com.aj.currencycalculator.util.extension.toUIModel
import javax.inject.Inject

class ObjectMapperImp @Inject constructor() : ObjectMapper {

    override fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity> {
        return listCurrency.map {
            it.toDBModel()
        }
    }

    override fun entityToUI(listCurrencyEntity: List<CurrencyRateEntity>): List<CurrencyUI> {
        return listCurrencyEntity.map {
            it.toUIModel()
        }
    }

    override fun historyEntityToUI(searchHistoryEntity: List<SearchHistoryEntity>): List<SearchHistoryUI.SearchHistory> {
        return searchHistoryEntity.map {
            it.toUIModel()
        }
    }
}
