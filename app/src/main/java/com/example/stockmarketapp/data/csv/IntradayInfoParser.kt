package com.example.stockmarketapp.data.csv

import com.example.stockmarketapp.data.mapper.toIntradayInfo
import com.example.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Calendar

class IntradayInfoParser : CSVParser<IntradayInfo> {
    private val calendar = Calendar.getInstance()
    private val previousDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).minus(1)

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble(),
                    )
                    dto.toIntradayInfo()
                }
                .filter { info ->
                    calendar.time = info.date
                    calendar.get(Calendar.DAY_OF_MONTH) == previousDate
                }
                .sortedBy { info ->
                    calendar.time = info.date
                    calendar.get(Calendar.HOUR)
                }
                .also {
                    csvReader.close()
                }
        }
    }
}