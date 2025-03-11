package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.pages.dialog.FullScreenDialog
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.smallPadding
import uk.gov.logging.api.Logger
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigField
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigProvider
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.InMemoryConfigStore
import uk.gov.onelogin.criorchestrator.testwrapper.R

@Composable
@Suppress("LongMethod")
internal fun DevMenu(
    state: DevMenuState,
    configStore: ConfigStore,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    if (!state.allowedToShow) {
        return
    }

    var config =
        remember {
            mutableStateOf<ConfigProvider>(
                ConfigProvider(
                    backendAsyncUrl = configStore.read(ConfigField.BackendAsyncUrl).value as String,
                ),
            )
        }

    var text by remember {
        mutableStateOf(
            configStore.read(ConfigField.BackendAsyncUrl).value as String,
        )
    }

    FullScreenDialog(
        modifier = modifier,
        onDismissRequest = state::onDismissRequest,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                value = text,
                onValueChange = { updatedValue ->
                    text = updatedValue
                    config.value =
                        ConfigProvider(
                            backendAsyncUrl = updatedValue,
                        )
                },
                label = { Text("Backend Async URL") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier =
                    Modifier
                        .align(Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                Button(
                    modifier =
                        Modifier
                            .padding(smallPadding),
                    onClick = {
                        configStore.write(config.value)
                        logger.debug(
                            "Dev Menu",
                            "New value for Backend Async URL is $text",
                        )
                    },
                ) {
                    Text("Update Config")
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
internal fun DevMenuPreview() {
    val logger = SystemLogger()
    val configStore = InMemoryConfigStore(logger)
    configStore.write(
        ConfigProvider(
            backendAsyncUrl =
                LocalContext.current.resources.getString(
                    R.string.backendAsyncUrl,
                ),
        ),
    )
    GdsTheme {
        DevMenu(
            state = DevMenuState(true),
            configStore = configStore,
            logger = logger,
            modifier = Modifier,
        )
    }
}
