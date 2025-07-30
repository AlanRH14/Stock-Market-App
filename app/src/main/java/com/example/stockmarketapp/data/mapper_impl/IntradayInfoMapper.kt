package com.example.stockmarketapp.data.mapper_impl

import com.example.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.example.stockmarketapp.domain.model.IntradayInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val dateParse = formatter.parse(timestamp)?.time ?: 0L
    val dateFormat = Date(dateParse)

    return IntradayInfo(
        date = dateFormat,
        close = close
    )
}