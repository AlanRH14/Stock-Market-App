package com.example.stockmarketapp.data.mapper_impl

import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.data.local.CompanyListingEntity
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.domain.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing

class CompanyApiMapper : ApiMapper<CompanyListingEntity, CompanyListing> {

    override fun mapToDomain(apiDto: CompanyListingEntity): CompanyListing {
        return CompanyListing(
            name = apiDto.name,
            symbol = apiDto.symbol,
            exchange = apiDto.exchange,
        )
    }
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}