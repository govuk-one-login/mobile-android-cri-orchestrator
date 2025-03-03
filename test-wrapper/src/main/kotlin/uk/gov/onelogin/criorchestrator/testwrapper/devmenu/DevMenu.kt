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
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.pages.dialog.FullScreenDialog
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.smallPadding
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.testwrapper.R

@Composable
@Suppress("LongMethod")
internal fun DevMenu(
    initialConfig: Config,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var config by
        remember {
            mutableStateOf<Config>(
                initialConfig,
            )
        }

    FullScreenDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            config.entries.forEach { field ->
                field.value.let {
                    when (it) {
                        is Config.Value.StringValue -> {
                            TextField(
                                value = it.value,
                                onValueChange = { updatedValue ->
                                    // TODO
                                },
                                label = { Text(text = field.key.name) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        else -> TODO()
                    }
                }
            }

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
                        // TODO
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
    val initialConfig =
        Config(
            entries =
                persistentListOf(
                    Config.Entry(
                        key = IdCheckAsyncBackendBaseUrl,
                        value =
                            Config.Value.StringValue(
                                value =
                                    LocalContext.current.resources.getString(
                                        R.string.backendAsyncUrl,
                                    ),
                            ),
                    ),
                ),
        )
    GdsTheme {
        DevMenu(
            initialConfig = initialConfig,
            onDismissRequest = {},
            modifier = Modifier,
        )
    }
}
