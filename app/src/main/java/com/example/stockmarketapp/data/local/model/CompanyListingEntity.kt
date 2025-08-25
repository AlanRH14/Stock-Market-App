package com.example.stockmarketapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val symbol: String,
    val exchange: String
)