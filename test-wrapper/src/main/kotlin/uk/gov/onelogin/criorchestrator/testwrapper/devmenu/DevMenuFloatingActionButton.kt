package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import uk.gov.onelogin.criorchestrator.testwrapper.R

@Composable
internal fun DevMenuFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = { Icon(painterResource(R.drawable.ic_settings), null) },
        text = { Text(text = "Developer settings") },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = FloatingActionButtonDefaults.smallShape,
    )
}
