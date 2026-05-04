package com.yavin.myapplication.navigation

object AppRoute {
    const val ParcelList = "parcel_list"
    const val ParcelMap = "parcel_map"
    const val Settings = "settings"
    const val CreateParcel = "create_parcel"
    const val ParcelIdArg = "parcelId"

    fun parcelMap(parcelId: String): String = "$ParcelMap/$parcelId"
}
