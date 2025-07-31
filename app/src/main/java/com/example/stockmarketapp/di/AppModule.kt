package com.example.stockmarketapp.di

import com.example.stockmarketapp.presentation.company_info.CompanyInfoViewModel
import com.example.stockmarketapp.presentation.company_listing.CompanyListingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CompanyListingsViewModel(get()) }
    viewModel { CompanyInfoViewModel(get()) }
}