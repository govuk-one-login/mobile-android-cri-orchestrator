package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

internal object SelectDrivingLicenceConstants {
    @StringRes val titleId: Int = R.string.selectdocument_drivinglicence_title

    @StringRes val readMoreButtonTextId: Int = R.string.selectdocument_drivinglicence_readmore_button

    @StringRes val buttonTextId: Int = R.string.selectdocument_drivinglicence_continuebutton

    val options: ImmutableList<Int> =
        persistentListOf(
            R.string.selectdocument_drivinglicence_selection_yes,
            R.string.selectdocument_drivinglicence_selection_no,
        )
}
