package com.aj.currencycalculator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateUpdateTimeEntity(
    @PrimaryKey val id: String,
    // From API
    val timeStampAPI: Long,
    val updatedAt: Long = System.currentTimeMillis() / 1000
)
