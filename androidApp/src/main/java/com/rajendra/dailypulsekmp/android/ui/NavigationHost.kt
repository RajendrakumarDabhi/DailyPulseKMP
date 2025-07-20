package com.rajendra.dailypulsekmp.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rajendra.dailypulsekmp.android.ui.home.NewsScreen
import com.rajendra.dailypulsekmp.presentation.viewmodel.NewsViewModel

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.HOME_SCREEN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME_SCREEN) {
            NewsScreen({})
        }
    }
}