package com.aj.currencycalculator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aj.currencycalculator.data.model.CurrencyRateUI

@Entity
data class CurrencyRateEntity(@PrimaryKey var code: String = "", var rate: Double = 0.0)

fun CurrencyRateEntity.toUIModel() = CurrencyRateUI(
    code = code, rate = rate
)
