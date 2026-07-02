package com.yavin.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yavin.myapplication.presentation.list.ParcelListRoute
import com.yavin.myapplication.presentation.map.ParcelMapViewModel
import com.yavin.myapplication.presentation.settings.SettingsViewModel
import com.yavin.myapplication.ui.parcel.create.CreateParcelScreen
import com.yavin.myapplication.ui.parcel.map.ParcelMapScreen
import com.yavin.myapplication.ui.settings.SettingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.ParcelList,
        modifier = modifier
    ) {
        composable(route = AppRoute.ParcelList) {
            ParcelListRoute(
                onParcelClick = { parcelId ->
                    navController.navigate(AppRoute.parcelMap(parcelId))
                },
                onSettingsClick = {
                    navController.navigate(AppRoute.Settings)
                },
                onAddParcelClick = {
                    navController.navigate(AppRoute.CreateParcel)
                }
            )
        }

        composable(
            route = "${AppRoute.ParcelMap}/{${AppRoute.ParcelIdArg}}",
            arguments = listOf(
                navArgument(AppRoute.ParcelIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: ParcelMapViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            ParcelMapScreen(
                state = uiState.value,
                onRouteReplayClick = viewModel::onRouteReplayClick,
                onCameraPositionChanged = viewModel::onCameraPositionChanged,
                onBackClick = { navController.popBackStack() },
                onSettingsClick = {
                    navController.navigate(AppRoute.Settings)
                }
            )
        }

        composable(route = AppRoute.Settings) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            SettingsScreen(
                themeMode = uiState.value.themeMode,
                onThemeModeClick = viewModel::onThemeModeSelected,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = AppRoute.CreateParcel) {
            CreateParcelScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
