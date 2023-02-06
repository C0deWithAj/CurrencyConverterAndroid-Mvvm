package com.aj.currencycalculator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateUpdateTimeEntity(
    @PrimaryKey val id: String,
    // From API
    val updatedAt: Long = System.currentTimeMillis() / 1000,
    val timeStamp: Long
)
