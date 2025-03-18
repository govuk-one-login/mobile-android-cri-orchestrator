package uk.gov.onelogin.criorchestrator.features.dev.internal.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.dev.internal.R

@Composable
internal fun DevMenu(
    config: Config,
    onEntryChange: (Config.Entry<Config.Value>) -> Unit,
    modifier: Modifier = Modifier,
) = Surface(
    modifier =
        Modifier
            .fillMaxWidth(),
    // DCMAW-11635
    color = MaterialTheme.colorScheme.background,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacingDouble),
    ) {
        item {
            Text(
                text = stringResource(R.string.developer_menu_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        items(
            items = config.entries.sortedBy { it.key.toString() },
            key = { it.key.id },
            itemContent = { entry ->
                ConfigEntry(
                    entry = entry,
                    onEntryChange = onEntryChange,
                )
            },
        )
    }
}

@Composable
@PreviewLightDark
internal fun DevMenuPreview() =
    GdsTheme {
        DevMenu(
            config = previewConfig,
            onEntryChange = {},
        )
    }
