package com.yavin.myapplication.presentation.settings

import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.data.sample.ParcelMockDataFactory
import javax.inject.Inject

class ImportMockParcelsUseCase @Inject constructor(
    private val parcelRepository: ParcelRepository,
    private val mockDataFactory: ParcelMockDataFactory
) {

    suspend operator fun invoke() {
        parcelRepository.replaceParcels(mockDataFactory.createParcels())
    }
}
