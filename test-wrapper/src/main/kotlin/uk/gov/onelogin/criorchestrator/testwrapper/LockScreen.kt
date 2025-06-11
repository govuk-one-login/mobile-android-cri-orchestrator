package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI

/**
 * TODO: Can we make this screen something more generic rather than 'lock' screen.
 *   'Lock' is a feature of the host app and CRI Orchestrator doesn't need to know about it.
 */
@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun LockScreen(
    modifier: Modifier = Modifier,
    onUnlock: () -> Unit,
) = LeftAlignedScreen(
    modifier = modifier,
    title = { horizontalPadding ->
        GdsHeading(
            text = "Locked",
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    },
    body = { horizontalPadding ->
        item {
            GdsButton(
                modifier =
                    Modifier
                        .padding(horizontal = horizontalPadding)
                        .fillMaxWidth(),
                text = "Unlock",
                onClick = onUnlock,
                buttonType = ButtonType.Primary,
            )
        }
    },
)
