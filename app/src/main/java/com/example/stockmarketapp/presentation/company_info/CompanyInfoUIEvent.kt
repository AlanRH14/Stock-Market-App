package com.example.stockmarketapp.presentation.company_info

interface CompanyInfoUIEvent {
    data class OnInit(val symbol: String) : CompanyInfoUIEvent
}