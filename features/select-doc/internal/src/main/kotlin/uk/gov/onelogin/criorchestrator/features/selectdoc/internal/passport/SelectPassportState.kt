package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.annotation.StringRes
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

data class SelectPassportState(
    @StringRes val titleId: Int = R.string.selectdocument_passport_title,
    @StringRes val readMoreButtonTextId: Int = R.string.selectdocument_passport_readmore_button,
    val options: PersistentList<Int> =
        persistentListOf(
            R.string.selectdocument_passport_selection_yes,
            R.string.selectdocument_passport_selection_no,
        ),
    @StringRes val buttonTextId: Int = R.string.selectdocument_passport_continuebutton,
    val selection: PassportSelection,
)

sealed class PassportSelection {
    data object Unselected : PassportSelection()

    data object Selected : PassportSelection()

    data object NotSelected : PassportSelection()
}
