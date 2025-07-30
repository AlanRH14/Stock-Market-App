package com.example.stockmarketapp.data.mapper_impl

import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.data.local.CompanyListingEntity
import com.example.stockmarketapp.domain.model.CompanyListing

class CompanyListingDomainMapper : ApiMapper<CompanyListing, CompanyListingEntity> {

    override fun mapToDomain(apiDto: CompanyListing): CompanyListingEntity {
        return CompanyListingEntity(
            name = apiDto.name,
            symbol = apiDto.symbol,
            exchange = apiDto.exchange
        )
    }
}