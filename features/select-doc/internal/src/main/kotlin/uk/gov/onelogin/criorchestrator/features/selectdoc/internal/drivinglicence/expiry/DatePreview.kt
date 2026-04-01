package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry

import androidx.compose.runtime.Composable
import uk.gov.android.ui.componentsv2.date.formatFullDate
import java.time.LocalDate

private object PreviewConstants {
    const val YEAR = 2026
    const val MONTH = 3
    const val DAY_OF_MONTH = 31
}

@Composable
internal fun previewEarliestAcceptableDrivingLicenceExpiryDateText(): String =
    with(PreviewConstants) {
        LocalDate
            .of(YEAR, MONTH, DAY_OF_MONTH)
            .formatFullDate()
    }
