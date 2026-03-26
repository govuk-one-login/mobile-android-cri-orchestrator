package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import java.time.LocalDate

data class SelectDrivingLicenseState(
    val displayReadMoreButton: Boolean,
    val earliestExpiryDate: LocalDate,
)
