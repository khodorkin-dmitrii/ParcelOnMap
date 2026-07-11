package com.yavin.myapplication.data.repository

import com.yavin.myapplication.data.model.Parcel
import kotlinx.coroutines.flow.Flow

interface ParcelRepository {
    fun observeParcels(): Flow<List<Parcel>>

    fun observeParcel(parcelId: String): Flow<Parcel?>

    suspend fun replaceParcels(parcels: List<Parcel>)
}
