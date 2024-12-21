package com.example.stockmarketapp.presentation.company_info

import com.example.stockmarketapp.domain.CompanyInfo
import com.example.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
