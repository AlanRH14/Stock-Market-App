package com.example.stockmarketapp.data.csv

import java.io.InputStream

interface CSVParser {
    suspend fun <T> parse(stream: InputStream): List<T>
}