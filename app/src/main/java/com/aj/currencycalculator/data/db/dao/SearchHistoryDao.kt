package com.aj.currencycalculator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aj.currencycalculator.data.db.entity.SearchHistoryEntity
import java.util.Date

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistoryEntity WHERE date BETWEEN :from AND :to")
    suspend fun getHistoryForDate(
        from: Date, to: Date
    ): List<SearchHistoryEntity>
}

