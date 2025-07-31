package com.example.stockmarketapp.presentation.company_info.mvi

interface CompanyInfoUIEvent {
    data class OnInit(val symbol: String) : CompanyInfoUIEvent
}