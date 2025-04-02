package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

internal object SelectBrpConstants {
    @StringRes
    val titleId: Int = R.string.selectdocument_brp_title

    @StringRes
    val readMoreButtonTextId: Int = R.string.selectdocument_brp_read_more_button

    @StringRes
    val continueButtonTextId: Int = R.string.selectdocument_brp_continue_button

    val selectionItems: ImmutableList<Int> =
        persistentListOf(
            R.string.selectdocument_brp_selection_yes,
            R.string.selectdocument_brp_selection_no,
        )
}
