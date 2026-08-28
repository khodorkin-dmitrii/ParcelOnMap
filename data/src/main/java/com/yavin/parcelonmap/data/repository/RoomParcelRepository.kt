package com.yavin.parcelonmap.data.repository

import com.yavin.parcelonmap.data.local.ParcelDao
import com.yavin.parcelonmap.data.local.toDomain
import com.yavin.parcelonmap.data.local.toEntity
import com.yavin.parcelonmap.data.local.toRoutePointEntities
import com.yavin.parcelonmap.data.model.Parcel
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
