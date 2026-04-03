package com.yavin.myapplication.di

import com.yavin.myapplication.data.repository.MockParcelRepository
import com.yavin.myapplication.data.repository.ParcelRepository

class AppContainer(
    val parcelRepository: ParcelRepository = MockParcelRepository()
)
