package com.example.stockmarketapp.presentation.company_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanyInfoViewModel(
    private val repository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CompanyInfoState())
    val state = _state.asStateFlow()

    fun onEvent(event: CompanyInfoUIEvent) {
        when (event) {
            is CompanyInfoUIEvent.OnInit -> {
                getCompanyInfo(symbol = event.symbol)
                getIntradayInfo(symbol = event.symbol)
            }
        }
    }

    private fun getCompanyInfo(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val companyResult = async { repository.getCompanyInfo(symbol = symbol) }
            when (val result = companyResult.await()) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            company = result.data,
                            error = null,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            company = null,
                        )
                    }
                }
            }
        }
    }

    private fun getIntradayInfo(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            val intradayInfoResult = async { repository.getIntradayInfo(symbol = symbol) }

            when (val result = intradayInfoResult.await()) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            stockInfo = result.data ?: emptyList(),
                            isLoading = false,
                            error = null,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            company = null,
                        )
                    }
                }
            }
        }
    }
}