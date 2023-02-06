package com.aj.currencycalculator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity

@Database(entities = [CurrencyRateEntity::class, CurrencyRateUpdateTimeEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyRateDao
    abstract fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeDao
}
