package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.annotation.StringRes
import kotlinx.collections.immutable.PersistentList

data class SelectPassportState(
    @StringRes val titleId: Int,
    @StringRes val readMoreButtonTextId: Int,
    val options: PersistentList<Int>,
    @StringRes val buttonTextId: Int,
    var selection: PassportSelection,
)

sealed class PassportSelection {
    data object Unselected : PassportSelection()

    data object Selected : PassportSelection()

    data object NotSelected : PassportSelection()
}
