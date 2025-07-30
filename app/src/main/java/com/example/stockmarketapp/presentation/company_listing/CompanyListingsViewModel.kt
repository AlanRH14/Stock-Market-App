package com.example.stockmarketapp.presentation.company_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CompanyListingsViewModel(
    private val repository: StockRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CompanyListingsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CompanyListingsEffect>()
    val effect = _effect.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.OnRefresh -> {
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingsEvent.OnSearchQueryChange -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }

            is CompanyListingsEvent.OnGetCompanyListings -> getCompanyListings()

            is CompanyListingsEvent.OnNavigateToCompanyInfo -> navigateToCompanyInfo(symbol = event.symbol)
        }
    }

    private fun getCompanyListings(
        fetchFromRemote: Boolean = false,
        query: String = _state.value.searchQuery.lowercase(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCompanyListing(fetchFromRemote = fetchFromRemote, query = query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _state.value = _state.value.copy(companies = listings)
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    fun updateRefresh(refresh: Boolean) {
        _state.value = _state.value.copy(isRefreshing = refresh)
    }

    private fun navigateToCompanyInfo(symbol: String) {
        viewModelScope.launch {
            _effect.emit(CompanyListingsEffect.NavigateToCompanyInfo(symbol = symbol))
        }
    }
}