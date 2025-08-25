package com.example.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockmarketapp.data.local.model.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}