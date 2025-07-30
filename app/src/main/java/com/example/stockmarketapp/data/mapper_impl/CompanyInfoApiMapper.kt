package com.example.stockmarketapp.data.mapper_impl

import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.domain.CompanyInfo

class CompanyInfoApiMapper : ApiMapper<CompanyInfoDto, CompanyInfo> {

    override fun mapToDomain(apiDto: CompanyInfoDto): CompanyInfo {
        return CompanyInfo(
            symbol = apiDto.symbol ?: "",
            description = apiDto.description ?: "",
            name = apiDto.name ?: "",
            country = apiDto.country ?: "",
            industry = apiDto.industry ?: ""
        )
    }
}