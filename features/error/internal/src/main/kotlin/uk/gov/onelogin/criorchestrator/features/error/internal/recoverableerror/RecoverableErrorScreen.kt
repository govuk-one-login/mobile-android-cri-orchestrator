@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonTypeV2
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.images.GdsIcon
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreenIcon
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.error.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun RecoverableErrorScreen(
    viewModel: RecoverableErrorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                RecoverableErrorAction.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    RecoverableErrorScreenContent(
        modifier = modifier,
        onButtonClick = viewModel::onButtonClick,
    )
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun RecoverableErrorScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
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
                    stringResource(RecoverableErrorConstants.titleId),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.error_recoverableerror_body1),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.error_recoverableerror_body2),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = stringResource(RecoverableErrorConstants.buttonTextId),
                    buttonType = ButtonTypeV2.Primary(),
                    onClick = dropUnlessResumed { onButtonClick() },
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun RecoverableErrorScreenPreview() {
    GdsTheme {
        RecoverableErrorScreenContent(
            onButtonClick = {},
        )
    }
}
