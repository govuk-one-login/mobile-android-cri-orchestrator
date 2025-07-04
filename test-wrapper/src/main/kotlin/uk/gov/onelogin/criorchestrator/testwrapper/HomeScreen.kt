package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun HomeScreen(
    criOrchestratorComponent: CriOrchestratorComponent,
    onRefreshActiveSessionClick: () -> Unit,
    modifier: Modifier = Modifier,
) = LeftAlignedScreen(
    modifier = modifier,
    title = { horizontalPadding ->
        GdsHeading(
            text = "Home",
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    },
    body = { horizontalPadding ->
        item {
            ProveYourIdentityCard(
                component = criOrchestratorComponent,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        }
        item {
            GdsButton(
                text = "Refresh active session",
                buttonType = ButtonType.Primary,
                onClick = onRefreshActiveSessionClick,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        }
    },
)
