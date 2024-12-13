package com.example.stockmarketapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmarketapp.presentation.company_listing.CompanyListingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CompanyListings.route
    ) {
       composable(Screen.CompanyListings.route) {
           CompanyListingsScreen(
               navigation = navController
           )
       }
    }
}