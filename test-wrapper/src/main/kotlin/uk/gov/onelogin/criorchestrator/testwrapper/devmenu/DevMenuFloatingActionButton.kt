package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsLocalColorScheme

@Composable
internal fun DevMenuFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = { Icon(Icons.Filled.Settings, null) },
        text = { Text(text = "Developer settings") },
        containerColor = GdsLocalColorScheme.current.disabledButtonContent,
    )
}
