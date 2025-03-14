package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config

@Composable
internal fun ConfigEntry(
    entry: Config.Entry<Config.Value>,
    onEntryChange: (Config.Entry<Config.Value>) -> Unit,
    modifier: Modifier = Modifier,
) = entry.value.let { value ->
    when (value) {
        is Config.Value.StringValue -> {
            ConfigStrEntry(
                modifier = modifier,
                name = entry.key.name,
                value = value.value,
                onValueChanged = {
                    onEntryChange(
                        entry.copy(value = Config.Value.StringValue(it)),
                    )
                },
            )
        }

        is Config.Value.BooleanValue ->
            ConfigBoolEntry(
                modifier = modifier,
                name = entry.key.name,
                value = value.value,
                onCheckedChange = {
                    onEntryChange(
                        entry.copy(value = Config.Value.BooleanValue(it)),
                    )
                },
            )
    }
}

@Composable
private fun ConfigBoolEntry(
    name: String,
    value: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) = ConfigEntryLayout(
    name = name,
    modifier = modifier,
) {
    Switch(
        checked = value,
        onCheckedChange = onCheckedChange,
    )
}

@Composable
private fun ConfigStrEntry(
    name: String,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) = ConfigEntryLayout(
    name = name,
    modifier = modifier,
) {
    TextField(
        shape = RectangleShape,
        value = value,
        onValueChange = onValueChanged,
    )
}

@Composable
private fun ConfigEntryLayout(
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                ).padding(horizontal = 16.dp),
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            text = name,
        )
        content()
    }
}

@Composable
@PreviewLightDark
internal fun ConfigEntryPreview() =
    GdsTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            previewConfig.entries.forEach {
                ConfigEntry(
                    entry = it,
                    onEntryChange = {},
                )
            }
        }
    }
