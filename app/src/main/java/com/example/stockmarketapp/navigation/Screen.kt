package com.example.stockmarketapp.navigation

import com.example.stockmarketapp.domain.utils.ScreenKeys.COMPANY_INFO
import com.example.stockmarketapp.domain.utils.ScreenKeys.COMPANY_LISTINGS

sealed class Screen(val route: String) {
    data object CompanyListings: Screen(route = COMPANY_LISTINGS)
    data object CompanyInfo: Screen(route = COMPANY_INFO)
}