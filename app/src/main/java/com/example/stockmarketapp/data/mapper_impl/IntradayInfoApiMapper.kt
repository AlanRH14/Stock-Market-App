package com.example.stockmarketapp.data.mapper_impl

import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.example.stockmarketapp.domain.model.IntradayInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IntradayInfoApiMapper : ApiMapper<IntradayInfoDto, IntradayInfo> {

    override fun mapToDomain(apiDto: IntradayInfoDto): IntradayInfo {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val dateParse = formatter.parse(apiDto.timestamp)?.time ?: 0L
        val dateFormat = Date(dateParse)

        return IntradayInfo(
            date = dateFormat,
            close = apiDto.close
        )
    }
}
