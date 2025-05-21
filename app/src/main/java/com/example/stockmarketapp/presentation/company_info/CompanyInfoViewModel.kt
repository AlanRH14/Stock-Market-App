package com.example.stockmarketapp.presentation.company_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CompanyInfoState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            _state.value = _state.value.copy(isLoading = true)
            val companyResult = async { repository.getCompanyInfo(symbol = symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol = symbol) }

            when (val result = companyResult.await()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        company = result.data,
                        isLoading = false,
                        error = null,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false,
                        company = null,
                    )
                }

                else -> Unit
            }

            when (val result = intradayInfoResult.await()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        stockInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false,
                        company = null,
                    )
                }

                else -> Unit
            }
        }
    }
}