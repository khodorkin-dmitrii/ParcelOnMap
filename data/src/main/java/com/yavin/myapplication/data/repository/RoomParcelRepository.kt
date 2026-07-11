package com.yavin.myapplication.data.repository

import com.yavin.myapplication.data.local.ParcelDao
import com.yavin.myapplication.data.local.toDomain
import com.yavin.myapplication.data.local.toEntity
import com.yavin.myapplication.data.local.toRoutePointEntities
import com.yavin.myapplication.data.model.Parcel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomParcelRepository(
    private val parcelDao: ParcelDao
) : ParcelRepository {

    override fun observeParcels(): Flow<List<Parcel>> = parcelDao
        .observeParcels()
        .map { parcels -> parcels.map { it.toDomain() } }

    override fun observeParcel(parcelId: String): Flow<Parcel?> = parcelDao
        .observeParcel(parcelId)
        .map { parcel -> parcel?.toDomain() }

    override suspend fun replaceParcels(parcels: List<Parcel>) {
        parcelDao.replaceParcels(
            parcels = parcels.map { it.toEntity() },
            routePoints = parcels.flatMap { it.toRoutePointEntities() }
        )
    }
}
