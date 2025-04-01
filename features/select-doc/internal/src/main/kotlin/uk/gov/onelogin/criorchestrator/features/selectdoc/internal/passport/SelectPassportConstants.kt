package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

internal object SelectPassportConstants {
    @StringRes
    val titleId: Int = R.string.selectdocument_passport_title

    @StringRes
    val readMoreButtonTextId: Int = R.string.selectdocument_passport_readmore_button

    @StringRes
    val buttonTextId: Int = R.string.selectdocument_passport_continuebutton

    val options: ImmutableList<Int> =
        persistentListOf(
            R.string.selectdocument_passport_selection_yes,
            R.string.selectdocument_passport_selection_no,
        )
}
