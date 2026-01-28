package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import uk.gov.android.ui.componentsv2.R.drawable.ic_external_site
import uk.gov.android.ui.componentsv2.R.string.opens_in_external_browser
import uk.gov.android.ui.componentsv2.button.ButtonTypeV2
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.button.GdsButtonDefaults
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.images.GdsIcon
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreenIcon
import uk.gov.android.ui.theme.m3.Buttons
import uk.gov.android.ui.theme.m3.ExtraTypography
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.m3.toMappedColors
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun FaceScanLimitReachedMobileScreen(
    viewModel: FaceScanLimitReachedMobileViewModel,
    webNavigator: WebNavigator,
    redirectUri: String,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = true) {
        // Back button should be disabled from this screen
        // as the user must return to their web browser
    }

    FaceScanLimitReachedMobileScreenContent(
        onButtonClick = viewModel::onButtonClick,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collect { action ->
            when (action) {
                FaceScanLimitReachedMobileAction.ContinueToGovUk -> {
                    webNavigator.openWebPage(redirectUri)
                }
            }
        }
    }
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
private fun FaceScanLimitReachedMobileScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        ErrorScreen(
            icon = { horizontalPadding ->
                GdsIcon(
                    image = ImageVector.vectorResource(ErrorScreenIcon.ErrorIcon.icon),
                    contentDescription = stringResource(ErrorScreenIcon.ErrorIcon.description),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                )
            },
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(FaceScanLimitReachedMobileConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    textAlign = GdsHeadingAlignment.CenterAligned,
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.handback_facescanlimitreached_body1),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_facescanlimitreachedmobile_body2_content_description)

                    Text(
                        text = stringResource(R.string.handback_facescanlimitreachedmobile_body2),
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
                val contentDescription = ". ${stringResource(opens_in_external_browser)}"
                GdsButton(
                    text = stringResource(FaceScanLimitReachedMobileConstants.buttonId),
                    buttonType =
                        ButtonTypeV2.Icon(
                            buttonColors = GdsButtonDefaults.defaultPrimaryColors(),
                            textStyle = ExtraTypography.bodyLargeBold,
                            icon = ImageVector.vectorResource(ic_external_site),
                            contentDescription = contentDescription,
                            shadowColor = Buttons.shadow.toMappedColors(),
                        ),
                    onClick = dropUnlessResumed { onButtonClick() },
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun FaceScanLimitReachedMobileScreen() {
    GdsTheme {
        FaceScanLimitReachedMobileScreenContent(
            onButtonClick = {},
        )
    }
}
