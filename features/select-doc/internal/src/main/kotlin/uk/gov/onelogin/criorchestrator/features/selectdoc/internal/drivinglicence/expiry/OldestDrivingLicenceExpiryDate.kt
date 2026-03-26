package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry

import dev.zacsweers.metro.Inject
import java.time.LocalDate

private const val MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO = 90L

/**
 * Returns the oldest acceptable driving licence expiry date.
 *
 * This is [MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO] days before today.
 */
@Inject
class OldestDrivingLicenceExpiryDate(
    private val today: () -> LocalDate = { LocalDate.now() },
) {
    operator fun invoke(): LocalDate = today().minusDays(MAX_DRIVING_LICENCE_EXPIRY_DAYS_AGO)
}
