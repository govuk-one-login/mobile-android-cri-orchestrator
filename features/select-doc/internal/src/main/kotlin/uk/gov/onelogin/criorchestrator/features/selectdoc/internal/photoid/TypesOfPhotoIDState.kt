package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import java.time.LocalDate

data class TypesOfPhotoIDState(
    val earliestExpiryDate: LocalDate,
    val enableExpiredDrivingLicences: Boolean,
)
