package com.example.stockmarketapp.common

interface ApiMapper<ApiDto, Domain> {
    fun mapToDomain(apiDto: ApiDto): Domain
}