package com.yavin.parcelonmap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.yavin.parcelonmap.presentation.create.CreateParcelRoute
import com.yavin.parcelonmap.presentation.list.ParcelListRoute
import com.yavin.parcelonmap.presentation.map.ParcelMapRoute
import com.yavin.parcelonmap.presentation.settings.SettingsRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(AppRoute.ParcelList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppRoute.ParcelList> {
                ParcelListRoute(
                    onParcelClick = { parcelId ->
                        backStack.add(AppRoute.ParcelMap(parcelId))
                    },
                    onSettingsClick = {
                        backStack.add(AppRoute.Settings)
                    },
                    onAddParcelClick = {
                        backStack.add(AppRoute.CreateParcel)
                    }
                )
            }

            entry<AppRoute.ParcelMap> { route ->
                ParcelMapRoute(
                    parcelId = route.parcelId,
                    onBackClick = { backStack.removeLastOrNull() },
                    onSettingsClick = {
                        backStack.add(AppRoute.Settings)
                    }
                )
            }

            entry<AppRoute.Settings> {
                SettingsRoute(
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }

            entry<AppRoute.CreateParcel> {
                CreateParcelRoute(
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
