package com.yavin.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yavin.myapplication.di.AppContainer
import com.yavin.myapplication.presentation.list.ParcelListViewModel
import com.yavin.myapplication.presentation.map.ParcelMapViewModel
import com.yavin.myapplication.presentation.settings.SettingsViewModel
import com.yavin.myapplication.ui.parcel.create.CreateParcelScreen
import com.yavin.myapplication.ui.parcel.list.ParcelListScreen
import com.yavin.myapplication.ui.parcel.map.ParcelMapScreen
import com.yavin.myapplication.ui.settings.SettingsScreen

@Composable
fun AppNavHost(
    appContainer: AppContainer,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.ParcelList,
        modifier = modifier
    ) {
        composable(route = AppRoute.ParcelList) {
            val viewModel: ParcelListViewModel = viewModel(
                factory = ParcelListViewModel.factory(appContainer.parcelRepository)
            )

            ParcelListScreen(
                state = viewModel.uiState,
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
        ) { backStackEntry ->
            val parcelId = backStackEntry.arguments?.getString(AppRoute.ParcelIdArg).orEmpty()
            val viewModel: ParcelMapViewModel = viewModel(
                factory = ParcelMapViewModel.factory(
                    repository = appContainer.parcelRepository,
                    parcelId = parcelId
                )
            )
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
            val viewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.factory(appContainer.appSettingsRepository)
            )
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
