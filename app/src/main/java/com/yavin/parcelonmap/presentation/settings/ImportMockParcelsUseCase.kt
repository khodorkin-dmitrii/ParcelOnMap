package com.yavin.parcelonmap.presentation.settings

import com.yavin.parcelonmap.data.repository.ParcelRepository
import com.yavin.parcelonmap.data.sample.ParcelMockDataFactory
import javax.inject.Inject

class ImportMockParcelsUseCase @Inject constructor(
    private val parcelRepository: ParcelRepository,
    private val mockDataFactory: ParcelMockDataFactory
) {

    suspend operator fun invoke() {
        parcelRepository.replaceParcels(mockDataFactory.createParcels())
    }
}
