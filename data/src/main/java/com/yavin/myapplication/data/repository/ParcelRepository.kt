package com.yavin.myapplication.data.repository

import com.yavin.myapplication.data.model.Parcel

interface ParcelRepository {
    fun getParcels(): List<Parcel>

    fun getParcel(parcelId: String): Parcel?
}
