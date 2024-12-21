package com.example.stockmarketapp.presentation.company_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stockmarketapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingsScreen(
    navigation: NavController,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val swipeRefresh = rememberPullToRefreshState()
    val state = viewModel.state
    val isRefreshing by remember { mutableStateOf(state.isRefreshing) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp),
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CompanyListingsEvent.OnSearchQueryChange(it)
                )
            },
            placeholder = {
                Text(text = stringResource(R.string.txt_search))
            },
            maxLines = 1,
            singleLine = true,
        )

        PullToRefreshBox(
            state = swipeRefresh,
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.onEvent(CompanyListingsEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(
                    items = state.companies,
                    key = { _, item -> item.symbol }) { index, company ->
                    CompanyItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                // TODO: Navigate to detail screen
                            },
                        company = company
                    )
                    if (index < state.companies.size) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            thickness = 2.dp
                        )
                    }
                }
            }
        }
    }
}