package com.yavin.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ParcelDao {

    @Transaction
    @Query("SELECT * FROM parcels ORDER BY trackingNumber")
    abstract fun observeParcels(): Flow<List<ParcelWithRoutePoints>>

    @Transaction
    @Query("SELECT * FROM parcels WHERE id = :parcelId")
    abstract fun observeParcel(parcelId: String): Flow<ParcelWithRoutePoints?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertParcels(parcels: List<ParcelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertRoutePoints(routePoints: List<ParcelRoutePointEntity>)

    @Query("DELETE FROM parcel_route_points")
    protected abstract suspend fun deleteRoutePoints()

    @Query("DELETE FROM parcels")
    protected abstract suspend fun deleteParcels()

    @Transaction
    open suspend fun replaceParcels(
        parcels: List<ParcelEntity>,
        routePoints: List<ParcelRoutePointEntity>
    ) {
        deleteRoutePoints()
        deleteParcels()
        insertParcels(parcels)
        insertRoutePoints(routePoints)
    }
}
