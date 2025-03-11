package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore

@Composable
internal fun DevMenuRoot(
    configStore: ConfigStore,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    val devMenuState =
        rememberDevMenuState(
            initiallyAllowedToShow = false,
        )

    DevMenuOpenButton(
        onClick = { devMenuState.allowToShow() },
        modifier = modifier,
    )

    DevMenu(
        state = devMenuState,
        configStore = configStore,
        logger = logger,
    )
}
