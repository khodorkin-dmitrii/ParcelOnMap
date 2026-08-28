package com.yavin.parcelonmap.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {

    @Serializable
    data object ParcelList : AppRoute

    @Serializable
    data class ParcelMap(val parcelId: String) : AppRoute

    @Serializable
    data object Settings : AppRoute

    @Serializable
    data object CreateParcel : AppRoute
}
