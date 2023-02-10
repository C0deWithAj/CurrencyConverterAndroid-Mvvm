package com.aj.currencycalculator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class CurrencyRateEntity(@PrimaryKey var code: String = "", var rate: Double = 0.0)

@Entity
data class CurrencyRateUpdateTimeEntity(
    @PrimaryKey val id: String,
    val timeStampAPI: Long,
    val updatedAt: Long = System.currentTimeMillis()
)

//TODO: Remove
@Entity
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null, var date: Date, var c: String,
    var toCurrency: String, var convertedValue: Double
)


@Entity
data class CurrencyHistoryEntity(
    @PrimaryKey var currencyCode: String
)