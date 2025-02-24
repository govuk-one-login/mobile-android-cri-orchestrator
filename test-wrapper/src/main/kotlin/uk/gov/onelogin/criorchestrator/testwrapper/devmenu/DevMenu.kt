package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import uk.gov.android.ui.pages.dialog.FullScreenDialog
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigProvider
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore

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

    var configProvider by remember {
        mutableStateOf(
            object : ConfigProvider {
                override val configMap = mutableMapOf<String, Any>()
            },
        )
    }

    var text by remember {
        mutableStateOf(
            "${configStore.readValueFromKey("backendAsyncUrl")}",
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
                    configProvider.configMap["backendAsyncUrl"] = updatedValue
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
                    modifier = Modifier,
                    onClick = {
                        configStore.writeProvidedConfig(
                            configProvider,
                        )
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
