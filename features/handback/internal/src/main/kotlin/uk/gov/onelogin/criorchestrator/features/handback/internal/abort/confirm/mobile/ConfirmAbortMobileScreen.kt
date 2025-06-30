package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.R.drawable.ic_external_site
import uk.gov.android.ui.componentsv2.R.string.opens_in_external_browser
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.m3_disabled
import uk.gov.android.ui.theme.m3_onDisabled
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmAbortMobileScreen(
    viewModel: ConfirmAbortMobileViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    fun onButtonClick() {
        viewModel.onContinueToGovUk()
    }

    when (state) {
        ConfirmAbortState.Loading -> LoadingScreen()

        ConfirmAbortState.Display ->
            ConfirmAbortMobileWebContent(
                onButtonClick = ::onButtonClick,
                modifier = modifier,
            )
    }

    LaunchedEffect(viewModel) {
        viewModel.onScreenStart()

        viewModel.actions.collect { action ->
            when (action) {
                is ConfirmAbortMobileAction.ContinueGovUk -> {
                    navController.navigate(
                        AbortDestinations.AbortedRedirectToMobileWebHolder(
                            redirectUri = action.redirectUri,
                        ),
                    )
                }

                ConfirmAbortMobileAction.NavigateToOfflineError ->
                    navController.navigate(
                        ErrorDestinations.RecoverableError,
                    )

                ConfirmAbortMobileAction.NavigateToUnrecoverableError ->
                    navController.navigate(
                        HandbackDestinations.UnrecoverableError,
                    )
            }
        }
    }
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmAbortMobileWebContent(
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
                    text = stringResource(ConfirmAbortMobileConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_confirmabort_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body1),
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

                item {
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body2),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
            },
            primaryButton = {
                val contentDescription = ". ${stringResource(opens_in_external_browser)}"
                GdsButton(
                    text = stringResource(ConfirmAbortMobileConstants.buttonId),
                    onClick = dropUnlessResumed { onButtonClick() },
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
                            contentDescription = contentDescription,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmAbortMobileWeb() {
    GdsTheme {
        ConfirmAbortMobileWebContent(onButtonClick = {})
    }
}
