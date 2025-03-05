package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui.DevMenu

/**
 * A screen that allows a developer to change SDK configuration.
 *
 * @param viewModel
 * @param modifier
 */
@Composable
internal fun DevMenuScreen(
    viewModel: DevMenuViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    DevMenu(
        modifier = modifier,
        onEntryChange = viewModel::onEntryChange,
        config = state.config,
    )
}
