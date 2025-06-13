package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun AnotherScreen(modifier: Modifier = Modifier) =
    CentreAlignedScreen(
        modifier = modifier,
        title = { horizontalPadding ->
            GdsHeading(
                text = "Another screen",
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        },
    )
