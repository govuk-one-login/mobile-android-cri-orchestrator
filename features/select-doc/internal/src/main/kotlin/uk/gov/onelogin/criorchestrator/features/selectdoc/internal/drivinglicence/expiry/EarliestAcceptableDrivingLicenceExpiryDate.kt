package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry

import dev.zacsweers.metro.Inject
import java.time.Clock
import java.time.LocalDate

private const val MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO = 90L

/**
 * Driving licences are accepted for a period of time ([MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO]) after they expire.
 *
 * Returns the earliest acceptable driving licence expiry date.
 */
@Inject
class EarliestAcceptableDrivingLicenceExpiryDate(
    private val clock: Clock,
) {
    private fun today() = LocalDate.now(clock)

    operator fun invoke(): LocalDate = today().minusDays(MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO)
}
