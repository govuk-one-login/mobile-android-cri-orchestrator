package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm

import java.time.LocalDate

data class ConfirmDrivingLicenceState(
    val earliestExpiryDate: LocalDate,
    val enableExpiredDrivingLicences: Boolean,
)
