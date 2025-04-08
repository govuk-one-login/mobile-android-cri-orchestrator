package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.collections.immutable.ImmutableList
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.OptionConfigKey

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
                options = (entry.key as? OptionConfigKey)?.options,
                onValueChange = {
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
    options: ImmutableList<String>?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }

    ConfigStrEntryRow(
        name = name,
        value = value,
        onClick = { showDialog = true },
        modifier = modifier,
    )

    if (showDialog) {
        if (options != null) {
            ConfigOptionsEntryDialog(
                name = name,
                options = options,
                value = value,
                onValueChange = onValueChange,
                onDismissRequest = { showDialog = false },
            )
        } else {
            ConfigStrEntryDialog(
                name = name,
                value = value,
                onValueChange = onValueChange,
                onDismissRequest = { showDialog = false },
            )
        }
    }
}

@Composable
private fun ConfigStrEntryRow(
    name: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier =
            modifier
                .clickable(
                    onClick = onClick,
                ).padding(horizontal = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ConfigStrEntryDialog(
    name: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                )
            }
        }
    }
}

@Composable
private fun ConfigOptionsEntryDialog(
    name: String,
    options: ImmutableList<String>,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
        ) {
            GdsSelection(
                title =
                    RadioSelectionTitle(
                        text = name,
                        titleType = TitleType.Heading,
                    ),
                items = options,
                selectedItem = options.indexOf(value),
                onItemSelected = {
                    onValueChange(options[it])
                },
                modifier = Modifier.padding(16.dp),
            )
        }
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

@Composable
@PreviewLightDark
internal fun ConfigStrEntryDialogPreview() =
    GdsTheme {
        ConfigStrEntryDialog(
            name = "name",
            value = "value",
            onValueChange = {},
            onDismissRequest = {},
        )
    }

@Composable
@PreviewLightDark
internal fun ConfigOptionsEntryDialogPreview() =
    GdsTheme {
        ConfigOptionsEntryDialog(
            name = "Name",
            value = "value",
            options = PreviewOptionConfigKey.options,
            onValueChange = {},
            onDismissRequest = {},
        )
    }
