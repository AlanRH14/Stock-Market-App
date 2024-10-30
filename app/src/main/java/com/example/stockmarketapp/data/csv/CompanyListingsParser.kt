package com.example.stockmarketapp.data.csv

import java.io.InputStream

class CompanyListingsParser: CSVParser {

    override suspend fun <T> parse(stream: InputStream): List<T> {
        TODO("Not yet implemented")
    }
}