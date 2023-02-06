package com.aj.currencycalculator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateUpdateTimeEntity(
    @PrimaryKey val id: String,
    val timeStampAPI: Long,
    val updatedAt: Long = System.currentTimeMillis()
)
