package com.example.stockmarketapp.presentation.company_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.domain.utils.Resource
import com.example.stockmarketapp.presentation.company_listing.mvi.CompanyListingsEffect
import com.example.stockmarketapp.presentation.company_listing.mvi.CompanyListingsState
import com.example.stockmarketapp.presentation.company_listing.mvi.CompanyListingsUIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanyListingsViewModel(
    private val repository: StockRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CompanyListingsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CompanyListingsEffect>()
    val effect = _effect.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsUIEvent) {
        when (event) {
            is CompanyListingsUIEvent.OnRefresh -> {
                _state.update { it.copy(isRefreshing = true) }
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingsUIEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }

            is CompanyListingsUIEvent.OnGetCompanyListings -> getCompanyListings()

            is CompanyListingsUIEvent.OnCompanyItemClickedUI -> navigateToCompanyInfo(symbol = event.symbol)
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
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        companies = listings,
                                        isRefreshing = false
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                        }
                    }
                }
        }
    }

    private fun navigateToCompanyInfo(symbol: String) {
        viewModelScope.launch {
            _effect.emit(CompanyListingsEffect.NavigateToCompanyInfo(symbol = symbol))
        }
    }
}