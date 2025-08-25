package com.example.stockmarketapp.di

import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.data.local.model.CompanyListingEntity
import com.example.stockmarketapp.data.mapper_impl.CompanyDomainMapper
import com.example.stockmarketapp.data.mapper_impl.CompanyEntityMapper
import com.example.stockmarketapp.data.mapper_impl.CompanyInfoApiMapper
import com.example.stockmarketapp.data.mapper_impl.IntradayInfoApiMapper
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.example.stockmarketapp.domain.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntradayInfo
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiMapperModule = module {

    single<ApiMapper<CompanyListingEntity, CompanyListing>>(named("CompanyEntityMapper")) { CompanyEntityMapper() }

    single<ApiMapper<CompanyListing, CompanyListingEntity>>(named("CompanyDomainMapper")) { CompanyDomainMapper() }

    single<ApiMapper<CompanyInfoDto, CompanyInfo>>(named("CompanyInfoApiMapper")) { CompanyInfoApiMapper() }

    single<ApiMapper<IntradayInfoDto, IntradayInfo>>(named("IntradayInfoApiMapper")) { IntradayInfoApiMapper() }
}