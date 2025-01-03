package com.example.stockmarketapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmarketapp.presentation.company_info.CompanyInfoScreen
import com.example.stockmarketapp.presentation.company_listing.CompanyListingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CompanyListings.route
    ) {
        composable(Screen.CompanyListings.route) {
            CompanyListingsScreen(
                modifier = modifier,
                navigation = navController
            )
        }

        composable("${Screen.CompanyInfo.route}/{symbol}") {
            CompanyInfoScreen(modifier = modifier)
        }
    }
}