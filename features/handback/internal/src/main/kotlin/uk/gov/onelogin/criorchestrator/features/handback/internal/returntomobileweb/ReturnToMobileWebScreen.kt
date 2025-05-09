package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.R.drawable.ic_external_site
import uk.gov.android.ui.componentsv2.R.string.opens_in_external_browser
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.m3_disabled
import uk.gov.android.ui.theme.m3_onDisabled
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ReturnToMobileWebScreen(
    viewModel: ReturnToMobileWebViewModel,
    webNavigator: WebNavigator,
    modifier: Modifier = Modifier,
) {
    BackHandler(true) {
        // Back button should be disabled from this screen
        // as the user must return to their desktop browser
    }

    ReturnToMobileWebScreenContent(
        onButtonClick = viewModel::onContinueToGovUk,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collect { action ->
            when (action) {
                is ReturnToMobileWebAction.ContinueToGovUk -> {
                    webNavigator.openWebPage(action.redirectUri)
                }
            }
        }
    }
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
private fun ReturnToMobileWebScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(ReturnToMobileWebConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    textAlign = GdsHeadingAlignment.CenterAligned,
                    customContentDescription =
                        stringResource(R.string.handback_returntomobileweb_title_content_description),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.handback_returntomobileweb_body1),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_returntomobileweb_body2_content_description)
                    Text(
                        text = stringResource(R.string.handback_returntomobileweb_body2),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding)
                                .semantics {
                                    contentDescription = customContentDescription
                                },
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = stringResource(ReturnToMobileWebConstants.buttonId),
                    buttonType =
                        ButtonType.Icon(
                            buttonColors =
                                ButtonDefaults.buttonColors(
                                    containerColor = colorScheme.primary,
                                    contentColor = colorScheme.onPrimary,
                                    disabledContainerColor = m3_disabled,
                                    disabledContentColor = m3_onDisabled,
                                ),
                            fontWeight = FontWeight.Bold,
                            iconImage = ImageVector.vectorResource(ic_external_site),
                            contentDescription = stringResource(opens_in_external_browser),
                        ),
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewReturnToMobileWebScreen() {
    GdsTheme {
        ReturnToMobileWebScreenContent(
            onButtonClick = {},
        )
    }
}
