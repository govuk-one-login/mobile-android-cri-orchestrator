package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
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
) = Surface(
    color = MaterialTheme.colorScheme.background,
) {
    Row(
        modifier =
            modifier
                .clickable(
                    role = Role.Switch,
                    onClick = { onCheckedChange(!value) },
                ).fillMaxWidth()
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            text = name,
        )
        Switch(
            checked = value,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
private fun ConfigStrEntry(
    name: String,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) = Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = modifier.padding(horizontal = 16.dp),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        TextField(
            shape = RectangleShape,
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier.fillMaxWidth(),
        )
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
