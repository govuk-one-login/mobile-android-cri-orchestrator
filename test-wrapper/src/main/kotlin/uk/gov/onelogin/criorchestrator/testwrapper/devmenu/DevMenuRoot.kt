package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config

@Composable
internal fun DevMenuRoot(
    initialConfig: Config,
    modifier: Modifier = Modifier,
) {
    var showDevMenu by remember { mutableStateOf(false) }

    DevMenuOpenButton(
        onClick = { showDevMenu = true },
        modifier = modifier,
    )

    if (showDevMenu) {
        DevMenu(
            onDismissRequest = { showDevMenu = false },
            initialConfig = initialConfig,
        )
    }
}
