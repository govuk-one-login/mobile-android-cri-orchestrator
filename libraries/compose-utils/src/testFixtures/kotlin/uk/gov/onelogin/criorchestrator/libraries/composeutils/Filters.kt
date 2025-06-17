package uk.gov.onelogin.criorchestrator.libraries.composeutils

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.isDialog

/**
 * Try to find any matching nodes in a dialog.
 *
 * If none exist, return the unfiltered nodes.
 *
 * May be useful for finding the top-most element given it's displayed in a dialog and underneath it.
 */
fun SemanticsNodeInteractionCollection.filterInDialogElseAll(): SemanticsNodeInteractionCollection {
    if (filter(hasAnyAncestor(isDialog())).fetchSemanticsNodes().any()) {
        return filter(hasAnyAncestor(isDialog()))
    }

    return this
}
